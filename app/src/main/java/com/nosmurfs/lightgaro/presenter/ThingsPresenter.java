package com.nosmurfs.lightgaro.presenter;

import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio on 11/01/2017.
 */

public class ThingsPresenter extends Presenter<ThingsPresenter.View> {

    private static final String TAG = "ThingsPresenter";

    private static final int MAX_RELAYS = 8;

    private List<Gpio> relays;

    public ThingsPresenter() {
        relays = new ArrayList<>();
    }

    @Override
    protected void initialize() {
        PeripheralManagerService peripheralManagerService = new PeripheralManagerService();
        List<String> portList = peripheralManagerService.getGpioList();
        if (portList.isEmpty()) {
            Log.i(TAG, "No GPIO port available on this device.");
        } else {
            try {
                initializeGpios(peripheralManagerService, portList, portList.size() < MAX_RELAYS ? portList.size() : MAX_RELAYS);
            } catch (IOException e) {
                view.showError("An error has ocurred");
            }
        }
    }

    private void initializeGpios(PeripheralManagerService peripheralManagerService, List<String> portList, int size) throws IOException {
        List<String> displayNames = new ArrayList<>();

        for (int index = 0; index < size; index++) {
            String port = portList.get(index);
            Gpio gpio = peripheralManagerService.openGpio(port);
            gpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            gpio.setActiveType(Gpio.ACTIVE_LOW);
            gpio.setValue(true);

            relays.add(gpio);

            displayNames.add(port);
        }

        if (size < MAX_RELAYS){
            for (int index = size; index < MAX_RELAYS; index++) {
                displayNames.add("NONE");
            }
        }

        showConnectionInformation(displayNames);
    }

    private void showConnectionInformation(List<String> displayNames) {
        // FIXME: 14/01/2017 Find better way
        view.showConnectionInformation(
                displayNames.get(0),
                displayNames.get(1),
                displayNames.get(2),
                displayNames.get(3),
                displayNames.get(4),
                displayNames.get(5),
                displayNames.get(6),
                displayNames.get(7)
        );
    }

    @Override
    public void destroy() {
        try {
            for (Gpio relay : relays) {
                relay.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface View extends Presenter.View{
        void showConnectionInformation(
                String relay1,
                String relay2,
                String relay3,
                String relay4,
                String relay5,
                String relay6,
                String relay7,
                String relay8
        );

        // TODO: 11/01/2017
    }
}
