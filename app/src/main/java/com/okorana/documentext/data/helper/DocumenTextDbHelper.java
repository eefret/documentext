package com.okorana.documentext.data.helper;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.okorana.documentext.data.Conversation;
import com.okorana.documentext.data.Message;

/**
 * Created by eefret on 5/7/16
 */
public class DocumenTextDbHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "documentext.db";
    private static final int DATABASE_VERSION = 1;

    public DocumenTextDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Message.class);
            TableUtils.createTable(connectionSource, Conversation.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
