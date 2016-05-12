/*
        * Copyright (C) 2008 Esmertec AG.
        * Copyright (C) 2008 The Android Open Source Project
        *
        * Licensed under the Apache License, Version 2.0 (the "License");
        * you may not use this file except in compliance with the License.
        * You may obtain a copy of the License at
        *
        *      http://www.apache.org/licenses/LICENSE-2.0
        *
        * Unless required by applicable law or agreed to in writing, software
        * distributed under the License is distributed on an "AS IS" BASIS,
        * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        * See the License for the specific language governing permissions and
        * limitations under the License.
        */
package com.okorana.documentext;

import android.app.Application;
import android.content.Context;
import android.drm.DrmManagerClient;
import android.os.StrictMode;
import android.provider.SearchRecentSuggestions;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.okorana.documentext.phonebookdata.Contact;
import com.okorana.documentext.phonebookdata.Conversation;
import com.okorana.documentext.phonebookdata.LogTag;
import com.okorana.documentext.phonebookdata.RecipientIdCache;

import java.util.Locale;

public class DocumenText extends Application {
    public static final String LOG_TAG = LogTag.TAG;
    private static DocumenText sMmsApp = null;
    private SearchRecentSuggestions mRecentSuggestions;
    private TelephonyManager mTelephonyManager;
    private String mCountryIso;
    private DrmManagerClient mDrmManagerClient;

    synchronized public static DocumenText getApplication() {
        return sMmsApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Log.isLoggable(LogTag.STRICT_MODE_TAG, Log.DEBUG)) {
            // Log tag for enabling/disabling StrictMode violation log. This will dump a stack
            // in the log that shows the StrictMode violator.
            // To enable: adb shell setprop log.tag.Mms:strictmode DEBUG
            StrictMode.setThreadPolicy(
                    new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
        }
        sMmsApp = this;
        // Load the default preference values
        // Figure out the country *before* loading contacts and formatting numbers
        Context context = getApplicationContext();
        Contact.init(this);
        Conversation.init(this);
        Contact.init(this);

    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


    /**
     * @return Returns the TelephonyManager.
     */
    public TelephonyManager getTelephonyManager() {
        if (mTelephonyManager == null) {
            mTelephonyManager = (TelephonyManager) getApplicationContext()
                    .getSystemService(Context.TELEPHONY_SERVICE);
        }
        return mTelephonyManager;
    }

    /**
     * Returns the content provider wrapper that allows access to recent searches.
     *
     * @return Returns the content provider wrapper that allows access to recent searches.
     */
    public SearchRecentSuggestions getRecentSuggestions() {
        /*
        if (mRecentSuggestions == null) {
            mRecentSuggestions = new SearchRecentSuggestions(this,
                    SuggestionsProvider.AUTHORITY, SuggestionsProvider.MODE);
        }
        */
        return mRecentSuggestions;
    }

    // This function CAN return null.
    public String getCurrentCountryIso() {
        return Locale.getDefault().getCountry();
    }

    public DrmManagerClient getDrmManagerClient() {
        if (mDrmManagerClient == null) {
            mDrmManagerClient = new DrmManagerClient(getApplicationContext());
        }
        return mDrmManagerClient;
    }
}
