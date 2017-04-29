package com.viralandroid.androidtabsatbottom.utils;

/**
 * Created by caoxuanphong on    4/28/16.
 */
public class StringUtils {

    public static int convertToInteger(String string) {
        return Integer.parseInt(string);
    }

    public static long convertToLong(String string) {
        return Long.parseLong(string);
    }

    public static byte[] toByteArray(String string) {
        return string.getBytes();
    }

    public static String trimZeros(String str) {
        int pos = str.indexOf(0);
        return pos == -1 ? str : str.substring(0, pos);
    }
}
