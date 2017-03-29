package com.neerajlal.testproject.contentproviders;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import com.neerajlal.testproject.accounts.AccountManagerUtil;

/**
 * Created by n33raj on 12-03-2016.
 */
public class ScoreObserver  extends ContentObserver {

    private Account mAccount;

    public ScoreObserver(Handler handler, Context context) {
        super(handler);
        mAccount = AccountManagerUtil.getInstance().getAccount(context);
    }

    @Override
    public void onChange(boolean selfChange) {
        onChange(selfChange, null);
    }

    @Override
    public void onChange(boolean selfChange, Uri changeUri) {
        super.onChange(selfChange);
        if (selfChange) {
            Bundle settingsBundle = new Bundle();
            //Forces a manual sync
            settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, false);
            //Forces the sync to start immediately.
            settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, false);
            ContentResolver.requestSync(mAccount, ScoreProvider.AUTHORITY, settingsBundle);
        }
    }
}
