package com.okorana.documentext.data.strategy;

import com.okorana.documentext.data.Conversation;

import java.util.List;

/**
 * Created by eefret on 5/8/16.
 */
public interface ConversationFinder {
    List<Conversation> findAll();
}
