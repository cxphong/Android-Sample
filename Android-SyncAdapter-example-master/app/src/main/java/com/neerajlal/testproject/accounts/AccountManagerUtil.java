package com.neerajlal.testproject.accounts;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

import com.neerajlal.testproject.utils.IConstants;

/**
 * Created by n33raj on 12-03-2016.
 */
public class AccountManagerUtil implements IConstants {

    private static AccountManagerUtil ourInstance = new AccountManagerUtil();
    private static Account mAccount;

    private AccountManagerUtil() {
    }

    public static AccountManagerUtil getInstance() {
        return ourInstance;
    }

    public Account getAccount(Context mContext){
        if(mAccount == null){
            mAccount = createSyncAccount(mContext);
        }
        return mAccount;
    }

    private Account createSyncAccount(Context context) {
        Account newAccount = new Account(ACCOUNT, ACCOUNT_TYPE);
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            return newAccount;
        } else {
            Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE);
            if (accounts.length > 0) {
                return accounts[0];
            }
        }
        return null;
    }

}
