package com.nosmurfs.lightgaro.model;

/**
 * Created by Sergio on 26/01/2017.
 */

public class DeviceDto {
    private String uniqueId;

    private UserDto user;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
