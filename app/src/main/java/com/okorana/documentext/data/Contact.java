package com.okorana.documentext.data;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eefret on 5/5/16.
 */
public class Contact {

    public static final Uri PHONE_CONTACTS_URI = ContactsContract.Contacts.CONTENT_URI;
    public static final String SORT_KEY = "sort_key";
    public static final String PHOTO_URI = "photo_uri";
    public static final String SEND_TO_VOICEMAIL = "send_to_voicemail";
    public static final String CONTACT_STATUS = "contact_status";
    public static final String CONTACT_STATUS_LABEL = "contact_status_label";
    public static final String PINNED = "pinned";
    public static final String DISPLAY_NAME = "display_name";
    public static final String PHONEBOOK_LABEL_ALT = "phonebook_label_alt";
    public static final String PHONEBOOK_BUCKET = "phonebook_bucket";
    public static final String IN_DEFAULT_DIRECTORY = "in_default_directory";
    public static final String PHOTO_ID = "photo_id";
    public static final String CUSTOM_RINGTONE = "custom_ringtone";
    public static final String _ID = "_id";
    public static final String TIMES_CONTACTED = "times_contacted";
    public static final String PHONEBOOK_LABEL = "phonebook_label";
    public static final String ACCOUNT_TYPE = "account_type";
    public static final String DISPLAY_NAME_ALT = "display_name_alt";
    public static final String LOOKUP = "lookup";
    public static final String PHONETIC_NAME = "phonetic_name";
    public static final String LAST_TIME_CONTACTED = "last_time_contacted";
    public static final String CONTACT_LAST_UPDATED_TIMESTAMP = "contact_last_updated_timestamp";
    public static final String HAS_PHONE_NUMBER = "has_phone_number";
    public static final String IN_VISIBLE_GROUP = "in_visible_group";
    public static final String DISPLAY_NAME_SOURCE = "display_name_source";
    public static final String PHOTO_FILE_ID = "photo_file_id";
    public static final String IS_USER_PROFILE = "is_user_profile";
    public static final String CONTACT_STATUS_TS = "contact_status_ts";
    public static final String SORT_KEY_ALT = "sort_key_alt";
    public static final String PHONEBOOK_BUCKET_ALT = "phonebook_bucket_alt";
    public static final String CONTACT_PRESENCE = "contact_presence";
    public static final String STARRED = "starred";
    public static final String PHOTO_THUMB_URI = "photo_thumb_uri";
    public static final String CONTACT_STATUS_ICON = "contact_status_icon";
    public static final String ACCOUNT_NAME = "account_name";
    public static final String PHONETIC_NAME_STYLE = "phonetic_name_style";
    public static final String NAME_RAW_CONTACT_ID = "name_raw_contact_id";

    private String sort_key;
    private String photo_uri;
    private int send_to_voicemail;
    private String contact_status;
    private int contact_status_label;
    private int pinned;
    private String display_name;
    private String phonebook_label_alt;
    private int phonebook_bucket;
    private int in_default_directory;
    private int photo_id;
    private String custom_ringtone;
    private int _id;
    private int times_contacted;
    private String phonebook_label;
    private String account_type;
    private String display_name_alt;
    private String lookup;
    private String phonetic_name;
    private int last_time_contacted;
    private int contact_last_updated_timestamp;
    private int has_phone_number;
    private int in_visible_group;
    private int display_name_source;
    private int photo_file_id;
    private int is_user_profile;
    private String contact_status_ts;
    private String sort_key_alt;
    private int phonebook_bucket_alt;
    private int contact_presence;
    private int starred;
    private int photo_thumb_uri;
    private int contact_status_icon;
    private String account_name;
    private String phonetic_name_style;
    private int name_raw_contact_id;

    public Contact(String sort_key, String photo_uri, int send_to_voicemail, String contact_status, int contact_status_label, int pinned, String display_name, String phonebook_label_alt, int phonebook_bucket, int in_default_directory, int photo_id, String custom_ringtone, int _id, int times_contacted, String phonebook_label, String account_type, String display_name_alt, String lookup, String phonetic_name, int last_time_contacted, int contact_last_updated_timestamp, int has_phone_number, int in_visible_group, int display_name_source, int photo_file_id, int is_user_profile, String contact_status_ts, String sort_key_alt, int phonebook_bucket_alt, int contact_presence, int starred, int photo_thumb_uri, int contact_status_icon, String account_name, String phonetic_name_style, int name_raw_contact_id) {
        this.sort_key = sort_key;
        this.photo_uri = photo_uri;
        this.send_to_voicemail = send_to_voicemail;
        this.contact_status = contact_status;
        this.contact_status_label = contact_status_label;
        this.pinned = pinned;
        this.display_name = display_name;
        this.phonebook_label_alt = phonebook_label_alt;
        this.phonebook_bucket = phonebook_bucket;
        this.in_default_directory = in_default_directory;
        this.photo_id = photo_id;
        this.custom_ringtone = custom_ringtone;
        this._id = _id;
        this.times_contacted = times_contacted;
        this.phonebook_label = phonebook_label;
        this.account_type = account_type;
        this.display_name_alt = display_name_alt;
        this.lookup = lookup;
        this.phonetic_name = phonetic_name;
        this.last_time_contacted = last_time_contacted;
        this.contact_last_updated_timestamp = contact_last_updated_timestamp;
        this.has_phone_number = has_phone_number;
        this.in_visible_group = in_visible_group;
        this.display_name_source = display_name_source;
        this.photo_file_id = photo_file_id;
        this.is_user_profile = is_user_profile;
        this.contact_status_ts = contact_status_ts;
        this.sort_key_alt = sort_key_alt;
        this.phonebook_bucket_alt = phonebook_bucket_alt;
        this.contact_presence = contact_presence;
        this.starred = starred;
        this.photo_thumb_uri = photo_thumb_uri;
        this.contact_status_icon = contact_status_icon;
        this.account_name = account_name;
        this.phonetic_name_style = phonetic_name_style;
        this.name_raw_contact_id = name_raw_contact_id;
    }

    public static Contact getFromCursor(Cursor c) {
        return new Contact(c.getString(c.getColumnIndex(SORT_KEY)),
                c.getString(c.getColumnIndex(PHOTO_URI)),
                c.getInt(c.getColumnIndex(SEND_TO_VOICEMAIL)),
                c.getString(c.getColumnIndex(CONTACT_STATUS)),
                c.getInt(c.getColumnIndex(CONTACT_STATUS_LABEL)),
                c.getInt(c.getColumnIndex(PINNED)),
                c.getString(c.getColumnIndex(DISPLAY_NAME)),
                c.getString(c.getColumnIndex(PHONEBOOK_LABEL_ALT)),
                c.getInt(c.getColumnIndex(PHONEBOOK_BUCKET)),
                c.getInt(c.getColumnIndex(IN_DEFAULT_DIRECTORY)),
                c.getInt(c.getColumnIndex(PHOTO_ID)),
                c.getString(c.getColumnIndex(CUSTOM_RINGTONE)),
                c.getInt(c.getColumnIndex(_ID)),
                c.getInt(c.getColumnIndex(TIMES_CONTACTED)),
                c.getString(c.getColumnIndex(PHONEBOOK_LABEL)),
                c.getString(c.getColumnIndex(ACCOUNT_TYPE)),
                c.getString(c.getColumnIndex(DISPLAY_NAME_ALT)),
                c.getString(c.getColumnIndex(LOOKUP)),
                c.getString(c.getColumnIndex(PHONETIC_NAME)),
                c.getInt(c.getColumnIndex(LAST_TIME_CONTACTED)),
                c.getInt(c.getColumnIndex(CONTACT_LAST_UPDATED_TIMESTAMP)),
                c.getInt(c.getColumnIndex(HAS_PHONE_NUMBER)),
                c.getInt(c.getColumnIndex(IN_VISIBLE_GROUP)),
                c.getInt(c.getColumnIndex(DISPLAY_NAME_SOURCE)),
                c.getInt(c.getColumnIndex(PHOTO_FILE_ID)),
                c.getInt(c.getColumnIndex(IS_USER_PROFILE)),
                c.getString(c.getColumnIndex(CONTACT_STATUS_TS)),
                c.getString(c.getColumnIndex(SORT_KEY_ALT)),
                c.getInt(c.getColumnIndex(PHONEBOOK_BUCKET_ALT)),
                c.getInt(c.getColumnIndex(CONTACT_PRESENCE)),
                c.getInt(c.getColumnIndex(STARRED)),
                c.getInt(c.getColumnIndex(PHOTO_THUMB_URI)),
                c.getInt(c.getColumnIndex(CONTACT_STATUS_ICON)),
                c.getString(c.getColumnIndex(ACCOUNT_NAME)),
                c.getString(c.getColumnIndex(PHONETIC_NAME_STYLE)),
                c.getInt(c.getColumnIndex(NAME_RAW_CONTACT_ID)));
    }

    public static Contact getFromID(Context ctx, Integer id){
        Cursor c = ctx.getContentResolver().query(
                PHONE_CONTACTS_URI,
                null,
                _ID + "=?",
                new String[]{String.valueOf(id)},
                null);
        if(c != null){
            c.moveToFirst();
            try {
                return Contact.getFromCursor(c);
            }finally {
                c.close();
            }
        }
        return null;
    }

    public static List<Contact> getList(Context ctx, ArrayList<Integer> contactIds) {
        ArrayList<Contact> contacts = new ArrayList<>(contactIds.size());
        Cursor c = ctx.getContentResolver().query(
                PHONE_CONTACTS_URI,
                null,
                _ID + " in (" + TextUtils.join(",", contactIds) + ")",
                null,
                null);
        if(c!= null){
            c.moveToFirst();
            while (c.moveToNext()){
                contacts.add(Contact.getFromCursor(c));
            }
            c.close();
            return contacts;
        }
        return null;
    }

    public Uri getLookupUri(){
        return Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI,lookup);
    }

    public InputStream getThumbnail(Context c) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, _id);
        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        Cursor cursor = c.getContentResolver().query(photoUri,
                new String[]{ContactsContract.Contacts.Photo.PHOTO}, null, null, null);
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.moveToFirst()) {
                byte[] data = cursor.getBlob(0);
                if (data != null) {
                    return new ByteArrayInputStream(data);
                }
            }
        } finally {
            cursor.close();
        }
        return null;
    }


    //Getters


    public String getSort_key() {
        return sort_key;
    }

    public String getPhoto_uri() {
        return photo_uri;
    }

    public int getSend_to_voicemail() {
        return send_to_voicemail;
    }

    public String getContact_status() {
        return contact_status;
    }

    public int getContact_status_label() {
        return contact_status_label;
    }

    public int getPinned() {
        return pinned;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getPhonebook_label_alt() {
        return phonebook_label_alt;
    }

    public int getPhonebook_bucket() {
        return phonebook_bucket;
    }

    public int getIn_default_directory() {
        return in_default_directory;
    }

    public int getPhoto_id() {
        return photo_id;
    }

    public String getCustom_ringtone() {
        return custom_ringtone;
    }

    public int get_id() {
        return _id;
    }

    public int getTimes_contacted() {
        return times_contacted;
    }

    public String getPhonebook_label() {
        return phonebook_label;
    }

    public String getAccount_type() {
        return account_type;
    }

    public String getDisplay_name_alt() {
        return display_name_alt;
    }

    public String getLookup() {
        return lookup;
    }

    public String getPhonetic_name() {
        return phonetic_name;
    }

    public int getLast_time_contacted() {
        return last_time_contacted;
    }

    public int getContact_last_updated_timestamp() {
        return contact_last_updated_timestamp;
    }

    public int getHas_phone_number() {
        return has_phone_number;
    }

    public int getIn_visible_group() {
        return in_visible_group;
    }

    public int getDisplay_name_source() {
        return display_name_source;
    }

    public int getPhoto_file_id() {
        return photo_file_id;
    }

    public int getIs_user_profile() {
        return is_user_profile;
    }

    public String getContact_status_ts() {
        return contact_status_ts;
    }

    public String getSort_key_alt() {
        return sort_key_alt;
    }

    public int getPhonebook_bucket_alt() {
        return phonebook_bucket_alt;
    }

    public int getContact_presence() {
        return contact_presence;
    }

    public int getStarred() {
        return starred;
    }

    public int getPhoto_thumb_uri() {
        return photo_thumb_uri;
    }

    public int getContact_status_icon() {
        return contact_status_icon;
    }

    public String getAccount_name() {
        return account_name;
    }

    public String getPhonetic_name_style() {
        return phonetic_name_style;
    }

    public int getName_raw_contact_id() {
        return name_raw_contact_id;
    }
}
