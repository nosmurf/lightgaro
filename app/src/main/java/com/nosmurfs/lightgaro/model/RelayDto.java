package com.nosmurfs.lightgaro.model;

/**
 * Created by Sergio on 26/01/2017.
 */

public class RelayDto {
    private String label;

    private boolean value;

    public RelayDto() {
    }

    public RelayDto(String label, boolean value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
