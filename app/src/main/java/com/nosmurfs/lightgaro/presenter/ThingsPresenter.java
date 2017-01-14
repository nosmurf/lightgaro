package com.nosmurfs.lightgaro.presenter;

import android.util.Log;
import android.widget.TextView;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;
import com.nosmurfs.lightgaro.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Sergio on 11/01/2017.
 */

public class ThingsPresenter extends Presenter<ThingsPresenter.View> {

    private static final String TAG = "ThingsPresenter";

    private static final int MAX_RELAYS = 8;

    private List<Gpio> relays;

    @BindView(R.id.relay_1)
    TextView relay1;

    @BindView(R.id.relay_2)
    TextView relay2;

    @BindView(R.id.relay_3)
    TextView relay3;

    @BindView(R.id.relay_4)
    TextView relay4;

    @BindView(R.id.relay_5)
    TextView relay5;

    @BindView(R.id.relay_6)
    TextView relay6;

    @BindView(R.id.relay_7)
    TextView relay7;

    @BindView(R.id.relay_8)
    TextView relay8;

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
        for (int index = 0; index < size; index++) {
            String port = portList.get(index);
            Gpio gpio = peripheralManagerService.openGpio(port);
            gpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            gpio.setActiveType(Gpio.ACTIVE_LOW);
            gpio.setValue(true);

            relays.add(gpio);

            // TODO: 14/01/2017 show relays in screen for help user
            Log.i(TAG, "initializeGpios: Relay " + index + " into " + port);
        }
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
        // TODO: 11/01/2017
    }
}
