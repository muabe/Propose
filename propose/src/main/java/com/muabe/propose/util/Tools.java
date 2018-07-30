package com.muabe.propose.util;

public class Tools {
    public static String getDefaultName(String key, Object obj) {
        return key+"-"+obj.hashCode();
    }
}
