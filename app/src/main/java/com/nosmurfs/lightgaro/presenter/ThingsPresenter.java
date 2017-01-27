package com.nosmurfs.lightgaro.presenter;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.nosmurfs.lightgaro.model.DeviceDto;
import com.nosmurfs.lightgaro.model.Relay;
import com.nosmurfs.lightgaro.model.RelayDto;
import com.nosmurfs.lightgaro.model.UserDto;
import com.nosmurfs.lightgaro.persistence.LightgaroPersistence;
import com.nosmurfs.lightgaro.persistence.Persistence;
import com.nosmurfs.lightgaro.util.UniqueIdGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio on 11/01/2017.
 */

public class ThingsPresenter extends Presenter<ThingsPresenter.View> {

    private static final String TAG = "ThingsPresenter";

    private static final int MAX_RELAYS = 8;

    private static final String RELAY_KEY = "relay";

    private static final String USER_KEY = "user";

    private List<Relay> relays;

    private DatabaseReference deviceReference;

    private DatabaseReference relayReference;

    private Persistence persistence;

    private String uniqueId;

    private DatabaseReference userReference;

    public ThingsPresenter() {
        relays = new ArrayList<>();
    }

    @Override
    protected void initialize() {
        // TODO: 26/01/2017 Resolve this with DI
        persistence = new LightgaroPersistence(view.getContext());

        initializeUniqueId();
        initializeFirebase();
        initializeHardware();
        createDeviceInFirebase();
        listenForChanges();
        listenForLogin();
    }


    private void initializeUniqueId() {
        if (persistence.hasUniqueId()) {
            uniqueId = persistence.getUniqueId();
        } else {
            uniqueId = UniqueIdGenerator.generate();
        }
        view.displayUniqueId(uniqueId);

    }

    private void initializeFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        deviceReference = database.getReference(uniqueId);
        relayReference = deviceReference.child(RELAY_KEY);
        userReference = deviceReference.child(USER_KEY);
    }

    private void initializeHardware() {
        PeripheralManagerService peripheralManagerService = new PeripheralManagerService();
        List<String> portList = peripheralManagerService.getGpioList();
        if (!portList.isEmpty()) {
            try {
                initializeGpios(peripheralManagerService, portList, portList.size() < MAX_RELAYS ? portList.size() : MAX_RELAYS);
            } catch (IOException e) {
                view.showError("An error has ocurred");
            }
        } else {
            view.showError("There are no available ports");
        }
    }

    private void initializeGpios(PeripheralManagerService peripheralManagerService, List<String> portList, int size)
            throws IOException {
        relays = new ArrayList<>();

        for (int index = 0; index < size; index++) {
            String port = portList.get(index);
            Gpio gpio = peripheralManagerService.openGpio(port);
            gpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            gpio.setActiveType(Gpio.ACTIVE_LOW);
            gpio.setValue(true);

            relays.add(new Relay(gpio, port, port));
        }

        if (size < MAX_RELAYS) {
            for (int index = size; index < MAX_RELAYS; index++) {
                relays.add(new Relay(null, "NONE"));
            }
        }

        view.showConnectionInformation(relays);
    }

    private void createDeviceInFirebase() {
        if (!persistence.hasUniqueId()) {
            persistence.saveUniqueId(uniqueId);

            DeviceDto deviceDto = new DeviceDto();
            deviceDto.setUser(new UserDto(false));
            deviceDto.setUniqueId(uniqueId);
            deviceReference.setValue(deviceDto);

            try {
                for (Relay relay : relays) {
                    RelayDto relayDto = new RelayDto(relay.getLabel(), relay.getGpio().getValue());
                    relayReference.child(relay.getId()).setValue(relayDto);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void listenForChanges() {
        if (relayReference != null) {
            relayReference.addChildEventListener(new RelayChangesListener());
        }
    }

    private void listenForLogin() {
        if (userReference != null) {
            userReference.addValueEventListener(new UserBindedListener());
        }
    }

    private void switchRelay(String key, Boolean value) {
        try {
            for (Relay relay : relays) {
                if (relay.getId().equals(key)) {
                    relay.getGpio().setValue(value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        view.showConnectionInformation(relays);
    }

    @Override
    public void destroy() {
        try {
            for (Relay relay : relays) {
                relay.getGpio().close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface View extends Presenter.View {
        void showConnectionInformation(List<Relay> relays);

        void displayUniqueId(String uniqueId);

        void showUserUI(String email, String name, String imageUrl);
    }

    private class RelayChangesListener implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            RelayDto relayDto = dataSnapshot.getValue(RelayDto.class);
            switchRelay(dataSnapshot.getKey(), relayDto.isValue());
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            RelayDto relayDto = dataSnapshot.getValue(RelayDto.class);
            switchRelay(dataSnapshot.getKey(), relayDto.isValue());
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            // Do nothing
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            // Do nothing
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Do nothing
        }
    }

    private class UserBindedListener implements ValueEventListener {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            UserDto userDto = dataSnapshot.getValue(UserDto.class);
            if (userDto.isBinded()) {
                view.showUserUI(userDto.getEmail(), userDto.getName(), userDto.getImageUrl());
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // TODO: 27/01/2017 display error
        }
    }
}
