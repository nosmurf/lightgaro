package com.nosmurfs.lightgaro.persistence;

/**
 * Created by Sergio on 26/01/2017.
 */

public interface Persistence {
    void saveUniqueId(String uniqueId);

    String getUniqueId();

    boolean hasUniqueId();

    void removeUniqueId();
}
