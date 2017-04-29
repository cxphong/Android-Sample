package com.viralandroid.androidtabsatbottom.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Progress dialog shows is processing
 *
 * Created by caoxuanphong on 12/3/16.
 */

public class ProcessingUI {
    ProgressDialog progressDialog;

    public void setMessage(String message) {
        try {
            progressDialog.setMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startProcessing(Context context) {
        startProcessing(context, "Processing ...");
    }

    public void startProcessing(Context context, String message) {
        progressDialog = ProgressDialog.show(context, "", message, true);
    }

    public void stopProcessing() {
        try {
            progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
