package com.catnip.quoteapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.catnip.quoteapp.data.local.dao.QuotesDao
import com.catnip.quoteapp.data.local.entity.QuoteEntity

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
@Database(entities = [QuoteEntity::class], version = 1, exportSchema = true)
abstract class QuoteDatabase : RoomDatabase() {
    abstract fun quotesDao(): QuotesDao

    companion object {
        private const val DB_NAME = "quote_db"
        fun create(context: Context): QuoteDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                QuoteDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
}