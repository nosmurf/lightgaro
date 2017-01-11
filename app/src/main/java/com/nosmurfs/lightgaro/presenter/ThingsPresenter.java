package com.nosmurfs.lightgaro.presenter;

import android.util.Log;

import com.google.android.things.pio.PeripheralManagerService;

import java.util.List;

/**
 * Created by Sergio on 11/01/2017.
 */

public class ThingsPresenter extends Presenter<ThingsPresenter.View> {

    private static final String TAG = "ThingsPresenter";

    @Override
    protected void initialize() {
        PeripheralManagerService peripheralManagerService = new PeripheralManagerService();
        List<String> portList = peripheralManagerService.getGpioList();
        if (portList.isEmpty()) {
            Log.i(TAG, "No GPIO port available on this device.");
        } else {
            Log.i(TAG, "List of available ports: " + portList);
        }
    }

    @Override
    public void destroy() {
        // TODO: 11/01/2017
    }

    public interface View extends Presenter.View{
        // TODO: 11/01/2017  
    }
}
