package com.example.fiszkiapp.database

import android.content.Context
import android.os.Environment
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Language::class, LangToLang::class, Topic::class, Flashcard::class, ToRepeat::class], version = 17, exportSchema = true)
abstract class FiszkiDatabase : RoomDatabase(){

    abstract val fiszkiDatabaseDao: FiszkiDatabaseDao


    companion object{
        @Volatile
        private var INSTANCE : FiszkiDatabase? = null
        private val DB_NAME = "fiszki_db"
        private val DB_PATH = String.format(
            "%s/%s",
            Environment.getExternalStorageDirectory().absolutePath + "/FiszkiApp/database", DB_NAME
        )
        fun getInstance(context: Context): FiszkiDatabase{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FiszkiDatabase::class.java,
                        DB_PATH//"fiszki_database"
                    ).fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }

}