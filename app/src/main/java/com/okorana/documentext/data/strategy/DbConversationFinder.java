package com.okorana.documentext.data.strategy;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.okorana.documentext.data.Conversation;
import com.okorana.documentext.data.helper.DocumenTextDbHelper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.ormlite.annotations.OrmLiteDao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by eefret on 5/8/16.
 */
@EBean
public class DbConversationFinder implements ConversationFinder {

    @RootContext
    Context ctx;

    @OrmLiteDao(helper= DocumenTextDbHelper.class)
    Dao<Conversation, Integer> conversationDao;

    @Override
    public List<Conversation> findAll() {
        try {
            return conversationDao.queryBuilder().orderBy(Conversation.DATE,false).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
