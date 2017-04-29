package com.viralandroid.androidtabsatbottom.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by caoxuanphong on    8/1/16.
 */
public class ErrorUtils {

    public static String exceptionToString(Exception e) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);

        return writer.toString();
    }
}
