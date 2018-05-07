package com.ulling.ullingcion.db;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ulling.lib.core.base.AppExecutors;

public class UpbitRoomDatabase extends RoomDatabase {
    private static UpbitRoomDatabase sInstance;
    public static final String DATABASE_NAME = "UpbitRoomDatabase-db";

    public static UpbitRoomDatabase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (UpbitRoomDatabase.class) {
                if (sInstance == null) {
//                    sInstance = buildDatabase(context.getApplicationContext(), executors);
//                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }


    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
}
