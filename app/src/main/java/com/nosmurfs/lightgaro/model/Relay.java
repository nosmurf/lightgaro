package com.nosmurfs.lightgaro.model;

import com.google.android.things.pio.Gpio;

/**
 * Created by Sergio on 22/01/2017.
 */

public class Relay {
    private Gpio gpio;

    private String label;

    public Relay() {
        super();
    }

    public Relay(Gpio gpio, String label) {
        this.gpio = gpio;
        this.label = label;
    }

    public Gpio getGpio() {
        return gpio;
    }

    public void setGpio(Gpio gpio) {
        this.gpio = gpio;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
