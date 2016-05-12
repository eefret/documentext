package com.okorana.documentext.activities.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.okorana.documentext.R;
import com.okorana.documentext.activities.MainActivity;
import com.okorana.documentext.data.Conversation;
import com.okorana.documentext.data.helper.DocumenTextDbHelper;
import com.okorana.documentext.ui.adapter.ConversationListAdapter;
import com.okorana.documentext.ui.itemview.ConversationItemView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.ormlite.annotations.OrmLiteDao;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by eefret on 5/3/16.
 */
@EFragment(R.layout.fragment_conversation_list)
public class ConversationListFragment extends Fragment{

    public static final String TAG = "ConversationListFragment__";

    @ViewById RecyclerView recycler;

    @OrmLiteDao(helper = DocumenTextDbHelper.class)
    Dao<Conversation,Integer> conversationDao;

    @Bean
    ConversationListAdapter adapter;

    RecyclerView.LayoutManager layoutManager;

    @AfterViews
    void bindAdapter(){
        recycler.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(layoutManager);
        adapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).onConversationClicked(((ConversationItemView)v).getConversation());
            }
        });
    }
}
