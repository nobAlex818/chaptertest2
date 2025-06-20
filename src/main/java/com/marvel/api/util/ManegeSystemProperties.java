package com.marvel.api.util;

public class ManegeSystemProperties {

    private ManegeSystemProperties() {
        throw new IllegalStateException("Utility class");
    }

    public static String setProp(String nameEnv, String param) {
        System.setProperty(nameEnv, param);
        return "OK";
    }

    public static String getProp(String nameEnv) {
        return System.getProperty(nameEnv);
    }
}
