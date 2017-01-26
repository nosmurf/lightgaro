package com.nosmurfs.lightgaro.model;

import com.google.android.things.pio.Gpio;

/**
 * Created by Sergio on 22/01/2017.
 */

public class Relay {
    private Gpio gpio;

    private String label;

    private String id;

    public Relay() {
        super();
    }

    public Relay(Gpio gpio, String label) {
        this.gpio = gpio;
        this.label = label;
    }

    public Relay(Gpio gpio, String label, String id) {
        this.gpio = gpio;
        this.label = label;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
