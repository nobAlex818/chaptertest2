package com.marvel.api.util;


public class EnvironmentConfig {
    public String getVariable(String variable) {
        String value = System.getenv(variable);//obtener desde variables de entorno
        return value == null ? "" : value;
    }

}