package com.nosmurfs.lightgaro.model;

/**
 * Created by Sergio on 26/01/2017.
 */

public class UserDto {
    private String name;

    private String email;

    private String imageUrl;

    private boolean binded;

    public UserDto() {
        super();
    }

    public UserDto(boolean binded) {
        this.binded = binded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isBinded() {
        return binded;
    }

    public void setBinded(boolean binded) {
        this.binded = binded;
    }
}
