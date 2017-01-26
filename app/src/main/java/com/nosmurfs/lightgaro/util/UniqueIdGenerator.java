package com.nosmurfs.lightgaro.util;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class UniqueIdGenerator {
    private static final int NUM_BITS = 130;

    public static String generate() {
        return new BigInteger(NUM_BITS, new SecureRandom()).toString(32);
    }
}
