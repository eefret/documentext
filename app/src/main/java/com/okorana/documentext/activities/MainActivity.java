package com.okorana.documentext.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.PersistableBundle;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.FrameLayout;

import com.okorana.documentext.R;
import com.okorana.documentext.data.Conversation;
import com.okorana.documentext.utils.SMSHelper;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentByTag;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

@EActivity(R.layout.activity_main)
@WindowFeature(Window.FEATURE_NO_TITLE)
public class MainActivity extends AppCompatActivity {

    @ViewById
    FrameLayout container;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fm = getSupportFragmentManager();


    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        //TODO implement functionality
    }
}
