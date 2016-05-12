package com.okorana.documentext.activities;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.j256.ormlite.dao.Dao;
import com.okorana.documentext.R;
import com.okorana.documentext.activities.fragments.ConversationListFragment;
import com.okorana.documentext.activities.fragments.ConversationListFragment_;
import com.okorana.documentext.data.Conversation;
import com.okorana.documentext.data.Message;
import com.okorana.documentext.data.helper.DocumenTextDbHelper;
import com.okorana.documentext.phonebookdata.ContactList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;
import org.androidannotations.ormlite.annotations.OrmLiteDao;

import java.sql.SQLException;
import java.util.ArrayList;

@EActivity(R.layout.activity_main)
@WindowFeature(Window.FEATURE_NO_TITLE)
public class MainActivity extends AppCompatActivity {

    @ViewById
    FrameLayout container;

    @OrmLiteDao(helper = DocumenTextDbHelper.class)
    Dao<Conversation, Integer> convesationDao;

    @OrmLiteDao(helper = DocumenTextDbHelper.class)
    Dao<Message, Integer> messageDao;

    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(MainActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
            syncConversations();
            syncMessages();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> arrayList) {
            Toast.makeText(MainActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new TedPermission(this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\\n\\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_CONTACTS, Manifest.permission.READ_SMS)
                .check();
    }

    @AfterViews
    void showConversationList() {
        getSupportFragmentManager()
                .beginTransaction().replace(container.getId(),
                ConversationListFragment_.builder().build(),
                ConversationListFragment.TAG)
                .commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        //TODO implement functionality
    }

    @Background
    void syncConversations() {
        Cursor c = getContentResolver().query(Conversation.CONTENT_URI, null, null, null, null);
        if (c == null) {
            return;
        }
        while(c.moveToNext()){
            com.okorana.documentext.phonebookdata.Conversation conversation = com.okorana.documentext.phonebookdata.Conversation.get(
                    MainActivity.this,c.getLong(c.getColumnIndex(Conversation._ID)),true);
            Conversation conv = Conversation.getFromPhoneBookConversation(conversation);
            try {
                conv.conversationName = ContactList.getByIds(
                        c.getString(c.getColumnIndex(Conversation.RECIPIENT_IDS)), true).formatNames(",");
                convesationDao.createOrUpdate(conv);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        c.close();
    }

    @Background
    void syncMessages() {
        Cursor c = getContentResolver().query(Message.CONTENT_URI, null, null, null, null, null);
        if (c == null) {
            return;
        }
        while (c.moveToNext()) {
            try {
                messageDao.createOrUpdate(Message.getFromCursor(c));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        c.close();
    }

    public void onConversationClicked(Conversation conversation){
        //TODO
    }
}
