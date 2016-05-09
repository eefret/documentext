package com.okorana.documentext.data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.dao.RawRowObjectMapper;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.PreparedUpdate;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseResults;
import com.j256.ormlite.table.ObjectFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by eefret on 5/3/16.
 */
public class Conversation {

    //Constants
    public static final Uri CONTENT_URI = Uri.parse("content://mms-sms/conversations?simple=true");
    public static final String _ID = "_id";
    public static final String DATE = "date";
    public static final String MESSAGE_COUNT = "message_count";
    public static final String RECIPIENT_IDS = "recipient_ids";
    public static final String SNIPPET = "snippet";
    public static final String SNIPPET_CS = "snippet_cs";
    public static final String READ = "read";
    public static final String ARCHIVED = "archived";
    public static final String TYPE = "type";
    public static final String ERROR = "error";
    public static final String HAS_ATTACHMENT = "has_attachment";
    public static final String UNREAD_MESSAGE_COUNT = "unread_message_count";

    //Fields
    @DatabaseField(id = true) private long _id;
    @DatabaseField private long date;
    @DatabaseField private int message_count;
    @DatabaseField private String recipient_ids;
    @DatabaseField private String snippet;
    @DatabaseField private int snippet_cs;
    @DatabaseField private int read;
    @DatabaseField private int archived;
    @DatabaseField private int type;
    @DatabaseField private int error;
    @DatabaseField private int has_attachment;
    @DatabaseField private int unread_message_count;
    @DatabaseField private String conversationName;

    Conversation() {
        //Needed by ormlite
    }

    private Conversation(long _id, int date, int message_count, String recipient_ids,
                         String snippet, int snippet_cs, int read, int archived,
                         int type, int error, int has_attachment, int unread_message_count,
                         String conversationName) {
        this._id = _id;
        this.date = date;
        this.message_count = message_count;
        this.recipient_ids = recipient_ids;
        this.snippet = snippet;
        this.snippet_cs = snippet_cs;
        this.read = read;
        this.archived = archived;
        this.type = type;
        this.error = error;
        this.has_attachment = has_attachment;
        this.unread_message_count = unread_message_count;
        this.conversationName = conversationName;
    }

    public static Conversation getFromCursor(Cursor c, String conversationName) {
        return new Conversation(
                c.getLong(c.getColumnIndex(_ID)),
                c.getInt(c.getColumnIndex(DATE)),
                c.getInt(c.getColumnIndex(MESSAGE_COUNT)),
                c.getString(c.getColumnIndex(RECIPIENT_IDS)),
                c.getString(c.getColumnIndex(SNIPPET)),
                c.getInt(c.getColumnIndex(SNIPPET_CS)),
                c.getInt(c.getColumnIndex(READ)),
                c.getInt(c.getColumnIndex(ARCHIVED)),
                c.getInt(c.getColumnIndex(TYPE)),
                c.getInt(c.getColumnIndex(ERROR)),
                c.getInt(c.getColumnIndex(HAS_ATTACHMENT)),
                c.getInt(c.getColumnIndex(UNREAD_MESSAGE_COUNT)),
                conversationName);
    }

    public static Conversation getFromPhoneBookConversation(com.okorana.documentext.phonebookdata.Conversation conversation){
        Conversation conv = new Conversation();
        conv.recipient_ids = conversation.
    }

    public static List<Conversation> getListFromCursor(Context ctx, Cursor c) {
        ArrayList<Conversation> conversations = new ArrayList<>(c.getCount());
        c.moveToFirst();
        try {
            while (c.moveToNext()) {
                Cursor cursor = ctx.getContentResolver().query(
                        Uri.parse("content://sms/inbox"),
                        new String[]{"_id", "thread_id", "address"},
                        "thread_id = ?", new String[]{String.valueOf(c.getInt(c.getColumnIndex(_ID)))},
                        null);
                String conversationName = "";
                if(cursor!= null && cursor.getCount()>0){
                    cursor.moveToFirst();
                    conversationName = cursor.getString(2);
                    cursor.close();
                }
                Conversation conversation = Conversation.getFromCursor(c, conversationName);
                ArrayList<Contact> contacts = (ArrayList<Contact>) conversation.getContactList(ctx);
                if (contacts.size()==1 && contacts.get(0).getDisplay_name()!= null
                        && !contacts.get(0).getDisplay_name().isEmpty()){
                    conversation.conversationName = conversation.getContactList(ctx).get(0).getDisplay_name();
                }
                conversations.add(conversation);
            }
        } finally {
            c.close();
        }
        return conversations;
    }

    public ArrayList<Integer> getRecipients() {
        ArrayList<Integer> recipients = new ArrayList<>();
        for (String id : recipient_ids.split(" ")) {
            recipients.add(Integer.valueOf(id));
        }
        return recipients;
    }

    public List<Contact> getContactList(Context c) {
        return Contact.getList(c, getRecipients());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Convesation(");
        sb.append(String.format("contacts{%1$s}", recipient_ids));
        sb.append(String.format("messages{%1$d}", message_count));
        sb.append(")");
        return sb.toString();
    }

    public long get_id() {
        return _id;
    }

    public long getDate() {
        return date;
    }

    public int getMessage_count() {
        return message_count;
    }

    public String getRecipient_ids() {
        return recipient_ids;
    }

    public String getSnippet() {
        return snippet;
    }

    public int getSnippet_cs() {
        return snippet_cs;
    }

    public int getRead() {
        return read;
    }

    public int getArchived() {
        return archived;
    }

    public int getType() {
        return type;
    }

    public int getError() {
        return error;
    }

    public int getHas_attachment() {
        return has_attachment;
    }

    public int getUnread_message_count() {
        return unread_message_count;
    }

    public String getConversationName() {
        return conversationName;
    }
}
