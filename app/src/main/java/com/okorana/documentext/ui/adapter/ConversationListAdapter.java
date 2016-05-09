package com.okorana.documentext.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.okorana.documentext.data.Conversation;
import com.okorana.documentext.data.strategy.DbConversationFinder;
import com.okorana.documentext.ui.itemview.ConversationItemView;
import com.okorana.documentext.ui.itemview.ConversationItemView_;
import com.okorana.documentext.ui.wrapper.ViewWrapper;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;

/**
 * Created by eefret on 5/8/16.
 */
@EBean
public class ConversationListAdapter extends RecyclerViewAdapterBase<Conversation, ConversationItemView> {

    @RootContext
    Context ctx;

    @Bean(DbConversationFinder.class)
    DbConversationFinder dbConversationFinder;

    @AfterInject
    @Background
    void initAdapter(){
        items = dbConversationFinder.findAll();
        refresh();
    }

    @UiThread
    void refresh(){
        notifyDataSetChanged();
    }

    @Override
    protected ConversationItemView onCreateItemView(ViewGroup parent, int viewType) {
        return ConversationItemView_.build(ctx);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<ConversationItemView> holder, int position) {
        ConversationItemView view = holder.getView();
        Conversation conversation = items.get(position);
        view.bind(conversation);
    }
}
