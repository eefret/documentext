package com.okorana.documentext.data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.j256.ormlite.field.DatabaseField;
import com.okorana.documentext.utils.SMSHelper;

import java.util.ArrayList;
import java.util.Currency;

/**
 * Created by eefret on 5/7/16.
 */
public class Message {

    //Constants
    public static final Uri CONTENT_URI = Uri.parse("content://sms/");
    public static final String _ID = "_id";
    public static final String THREAD_ID = "thread_id";
    public static final String ADDRESS = "address";
    public static final String PERSON = "person";
    public static final String DATE = "date";
    public static final String DATE_SENT = "date_sent";
    public static final String READ = "read";
    public static final String STATUS = "status";
    public static final String TYPE = "type";
    public static final String BODY = "body";
    public static final String LOCKED = "locked";
    public static final String SUB_ID = "sub_id";
    public static final String PHONE_ID = "phone_id";
    public static final String ERROR_CODE = "error_code";
    public static final String CREATOR = "creator";
    public static final String SEEN = "seen";
    public static final String PRIORITY = "priority";

    //Fields
    @DatabaseField(id = true) private int _id;
    @DatabaseField private int thread_id;
    @DatabaseField private String address;
    @DatabaseField private int person;
    @DatabaseField private int date;
    @DatabaseField private int date_sent;
    @DatabaseField private int read;
    @DatabaseField private int status;
    @DatabaseField private int type;
    @DatabaseField private String body;
    @DatabaseField private int locked;
    @DatabaseField private int sub_id;
    @DatabaseField private int phone_id;
    @DatabaseField private int error_code;
    @DatabaseField private String creator;
    @DatabaseField private int seen;
    @DatabaseField private int priority;

    Message() {
        //Required by ormlite
    }

    private Message(int _id, int thread_id, String address, int person, int date, int date_sent, int read, int status, int type, String body, int locked, int sub_id, int phone_id, int error_code, String creator, int seen, int priority) {
        this._id = _id;
        this.thread_id = thread_id;
        this.address = address;
        this.person = person;
        this.date = date;
        this.date_sent = date_sent;
        this.read = read;
        this.status = status;
        this.type = type;
        this.body = body;
        this.locked = locked;
        this.sub_id = sub_id;
        this.phone_id = phone_id;
        this.error_code = error_code;
        this.creator = creator;
        this.seen = seen;
        this.priority = priority;
    }

    public static ArrayList<Message> getListFromThread(Context ctx, int threadID){
        Cursor c = ctx.getContentResolver().query(SMSHelper.SMS_CONTENT_PROVIDER,
                null,THREAD_ID +" = ?", new String[]{String.valueOf(threadID)},DATE + " DESC");
        ArrayList<Message> messages = new ArrayList<>();
        if(c != null){
            while (c.moveToNext()){
                messages.add(Message.getFromCursor(c));
            }
            c.close();
        }
        return messages;
    }

    public static Message getFromCursor(Cursor c){
        return new Message(c.getInt(c.getColumnIndex(_ID)),
                c.getInt(c.getColumnIndex(THREAD_ID)),
                c.getString(c.getColumnIndex(ADDRESS)),
                c.getInt(c.getColumnIndex(PERSON)),
                c.getInt(c.getColumnIndex(DATE)),
                c.getInt(c.getColumnIndex(DATE_SENT)),
                c.getInt(c.getColumnIndex(READ)),
                c.getInt(c.getColumnIndex(STATUS)),
                c.getInt(c.getColumnIndex(TYPE)),
                c.getString(c.getColumnIndex(BODY)),
                c.getInt(c.getColumnIndex(LOCKED)),
                c.getInt(c.getColumnIndex(SUB_ID)),
                c.getInt(c.getColumnIndex(PHONE_ID)),
                c.getInt(c.getColumnIndex(ERROR_CODE)),
                c.getString(c.getColumnIndex(CREATOR)),
                c.getInt(c.getColumnIndex(SEEN)),
                c.getInt(c.getColumnIndex(PRIORITY)));
    }
}
