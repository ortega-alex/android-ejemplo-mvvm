package com.dev.examplemvvm.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dev.examplemvvm.data.database.entities.QuoteEntity

@Dao
interface QuoteDao {

    @Query(value = "SELECT * FROM quote_table ORDER BY author DESC")
    suspend fun getAllQuote():List<QuoteEntity>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quotes:List<QuoteEntity>)

    @Query("DELETE FROM quote_table")
    suspend fun  deleteAllQuotes()
}