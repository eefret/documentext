package com.okorana.documentext.data;

import android.net.Uri;
import android.provider.Telephony;

/**
 * Created by eefret on 5/3/16.
 */
public class Conversation {
    private static final String TAG = "Mms/conv";
    private static final boolean DEBUG = false;
    private static final boolean DELETEDEBUG = false;

    public static final Uri sAllThreadsUri =
            Telephony.Threads.CONTENT_URI.buildUpon().appendQueryParameter("simple", "true").build();

    public static final String[] ALL_THREADS_PROJECTION = {
            Telephony.Threads._ID, Telephony.Threads.DATE, Telephony.Threads.MESSAGE_COUNT, Telephony.Threads.RECIPIENT_IDS,
            Telephony.Threads.SNIPPET, Telephony.Threads.SNIPPET_CHARSET, Telephony.Threads.READ, Telephony.Threads.ERROR,
            Telephony.Threads.HAS_ATTACHMENT
    };

    public static final String[] UNREAD_PROJECTION = {
            Telephony.Threads._ID,
            Telephony.Threads.READ
    };

    private static final String UNREAD_SELECTION = "(read=0 OR seen=0)";

    private static final String[] SEEN_PROJECTION = new String[]{
            "seen"
    };

    public static final int ID = 0;
    public static final int DATE = 1;
    public static final int MESSAGE_COUNT = 2;
    public static final int RECIPIENT_IDS = 3;
    public static final int SNIPPET = 4;
    public static final int SNIPPET_CS = 5;
    public static final int READ = 6;
    public static final int ERROR = 7;
    public static final int HAS_ATTACHMENT = 8;

}
