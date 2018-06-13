package com.ulling.ullingcion.entites.Cryptowat;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CandlesDao   {

    //    @Insert(onConflict = IGNORE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertCandle(Candles candle);

    @Query("SELECT * FROM candles")
    public LiveData<List<Candles>> getAllCandles();

//    @Query("select * from user")
//    List<User> loadAllUsers();
}
