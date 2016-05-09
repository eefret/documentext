package com.okorana.documentext.data.strategy;

import com.okorana.documentext.data.Message;

import java.util.List;

/**
 * Created by eefret on 5/8/16.
 */
public interface MessagesFinder {
    List<Message> findAll();
    List<Message> findByThreadID(Integer threadID);
}
