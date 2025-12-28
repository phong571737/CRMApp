package com.example.crmmobile.QuoteDirectory;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface QuoteDao {
    @Insert
    long addQuote(Quote quote);

    @Update
    int updateQuote(Quote quote);

    @Delete
    void delete(Quote quote);

    @Query("DELETE FROM BAOGIA WHERE ID = :id")
    void DeleteQuoteByID(int id);

    @Query("SELECT * FROM BAOGIA ORDER BY ID DESC")
    LiveData<List<Quote>> getAllQuotes();
}
