package com.okorana.documentext.activities.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.okorana.documentext.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by eefret on 5/3/16.
 */
@EFragment(R.layout.fragment_conversation)
public class ConversationFragment extends Fragment{
    public static final String TAG = "ConversationFragment__";

    @ViewById RecyclerView recycler;

    @AfterViews
    void bindAdapter(){
        recycler.setAdapter();
    }

}
