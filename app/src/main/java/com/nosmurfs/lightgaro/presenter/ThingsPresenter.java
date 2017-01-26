package com.nosmurfs.lightgaro.presenter;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.nosmurfs.lightgaro.model.Relay;
import com.nosmurfs.lightgaro.model.RelayDto;
import com.nosmurfs.lightgaro.util.UniqueIdGenerator;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio on 11/01/2017.
 */

public class ThingsPresenter extends Presenter<ThingsPresenter.View> {

    private static final String TAG = "ThingsPresenter";

    private static final int MAX_RELAYS = 8;

    private List<Relay> relays;

    private DatabaseReference relaysReference;

    public ThingsPresenter() {
        relays = new ArrayList<>();
    }

    @Override
    protected void initialize() {

        Log.i(TAG, "initialize: " + UniqueIdGenerator.generate());

        initializeHardware();
        initializeFirebase();
        listenForChanges();
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

    private void initializeFirebase() {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        relaysReference = database.getReference("relay");

        try {
            for (Relay relay : relays) {
                RelayDto relayDto = new RelayDto(relay.getLabel(), relay.getGpio().getValue());
                relaysReference.child(relay.getId()).setValue(relayDto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listenForChanges() {
        if (relaysReference != null) {
            relaysReference.addChildEventListener(new ChildEventListener() {
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
            });
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
    }
}
