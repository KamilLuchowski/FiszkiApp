package com.example.fiszkiapp.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors

@Database(entities = [Language::class, LangToLang::class, Topic::class, Flashcard::class, ToRepeat::class], version = 15, exportSchema = true)
abstract class FiszkiDatabase : RoomDatabase(){

    abstract val fiszkiDatabaseDao: FiszkiDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE : FiszkiDatabase? = null

        fun getInstance(context: Context): FiszkiDatabase{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FiszkiDatabase::class.java,
                        "fiszki_database"
                    ).fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }

}