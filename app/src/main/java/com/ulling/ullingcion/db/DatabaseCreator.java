package com.ulling.ullingcion.db;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

public class DatabaseCreator {
    private static final String DB_NAME = "ulling_coin.db";
    private static RoomLocalData INSTANCE;
    private static final Object LOCK = new Object();

    public synchronized static RoomLocalData getRoomLocalData(Context qCon) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(qCon,
                            RoomLocalData.class, DB_NAME)
//                    // To simplify the codelab, allow queries on the main thread.
//                    // Don't do this on a real app! See PersistenceBasicSample for an example.
//                    .allowMainThreadQueries()
                            .build();

                    INSTANCE = create(qCon, false);
                }
            }
        }
        return INSTANCE;
    }

    static RoomLocalData create(Context qCon, boolean memoryOnly) {
        RoomDatabase.Builder<RoomLocalData> b;

        if (memoryOnly) {
            b = Room.inMemoryDatabaseBuilder(qCon.getApplicationContext(), RoomLocalData.class);
        } else {
            b = Room.databaseBuilder(qCon.getApplicationContext(), RoomLocalData.class, DB_NAME);
        }

        return (b.build());
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
