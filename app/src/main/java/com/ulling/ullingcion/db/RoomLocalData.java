package com.ulling.ullingcion.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.ulling.ullingcion.entites.Cryptowat.Candles;
import com.ulling.ullingcion.entites.Cryptowat.CandlesDao;

@Database(entities = {Candles.class}, version = 1, exportSchema = false)
public abstract class RoomLocalData extends RoomDatabase {
    public abstract CandlesDao candlesDatabase();


}
