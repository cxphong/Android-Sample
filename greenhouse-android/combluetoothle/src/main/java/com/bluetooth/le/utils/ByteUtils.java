package com.bluetooth.le.utils;

import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * Created by caoxuanphong on    4/28/16.
 */
public class ByteUtils {
    private static final String TAG = "ByteUtils";

    public static byte[] stringToByteArray(String string) {
        return string.getBytes();
    }

    public static byte[] integerToByteArray(int number) {
        return ByteBuffer.allocate(4).putInt(number).array();
    }

    public static byte[] longToByteArray(long number) {
        return ByteBuffer.allocate(8).putLong(number).array();
    }

    public static byte[] add2ByteArray(byte[] a, byte[] b) {
        if (a == null && b == null) return null;
        if (a == null && b != null) return b;
        if (a != null && b == null) return a;

        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);

        return c;
    }

    public static byte[] addByte(byte[] a, byte b) {
        byte[] c;
        if (a == null) {
            return new byte[] {b};
        } else {
            c = new byte[a.length + 1];
        }


        if (a != null) {
            System.arraycopy(a, 0, c, 0, a.length);
            System.arraycopy(new byte[]{b}, 0, c, a.length, 1);
        } else {
            c[0] = b;
        }

        return c;
    }

    public static byte[] append(byte[] src, byte[] bytes, int startPos) {
        int j = 0;
        for (int i = startPos; i < startPos + bytes.length; i++) {
            src[i] = bytes[j++];
        }

        return src;
    }

    public static byte[] subByteArray(byte[] src, int startPos, int num) {
        int endPos = 0;

        if (startPos + num > src.length) {
            endPos = src.length;
        } else {
            endPos = startPos + num;
        }

        return Arrays.copyOfRange(src, startPos, endPos );
    }

    // the first byte the most significant
    public static long toLongFirstMostSignificant(byte[] bytes) {
        long value = 0;
        for (int i = 0; i < bytes.length; i++) {
            value = (value << 8) + (bytes[i] & 0xff);
        }

        return value;
    }

    // the first byte the least significant
    public static long toLongFirstLeastSignificant(byte[] bytes) {
        long value = 0;
        for (int i = 0; i < bytes.length; i++) {
            value += ((long) bytes[i] & 0xffL) << (8 * i);
        }

        return value;
    }

    public static String[] toHex(byte[] a) {
        if (a == null) return null;

        String[] s = new String[a.length];
        for (int  i = 0; i < a.length; i++) {
            s[i] = "0x" + Integer.toHexString((a[i] & 0xff));
        }

        return s;
    }

    public static String[] toHex(byte[] a, int length) {
        if (a == null) return null;

        String[] s = new String[length];
        for (int  i = 0; i < length; i++) {
            s[i] = "0x" + Integer.toHexString((a[i] & 0xff));
        }

        return s;
    }

    public static boolean compare2Array(byte[] b1, byte[] b2) {
        if (b1.length != b2.length) return false;

        for (int i = 0; i < b1.length; i++) {
            if (b1[i] != b2[i]) return false;
        }

        return true;
    }

    public static int getHi(byte b) {
        return (((b&0xff) & 0xf0) >> 4);
    }

    public static int getLo(byte b) {
        return (((b&0xff) & 0x0F));
    }

    public static int merge2Bytes(byte bHi, byte bLo) {
        return ((bHi&0xff) << 8) + (bLo&0xff);
    }

    public static String toString(byte[] b) {
        if (b == null) {
            return null;
        }

        return new String(b);
    }

    public static void printArray(String title, byte [] data) {
        Log.i(TAG, title + ", " + Arrays.toString(toHex(data))  + ", " + new String(data));
    }

    public static float toFloat(byte[] b) {
        return ByteBuffer.wrap(b).order(ByteOrder.BIG_ENDIAN).getFloat();
    }

    public static int toInteger(byte[] b) {
        return ByteBuffer.wrap(b).order(ByteOrder.BIG_ENDIAN).getInt();
    }
}
