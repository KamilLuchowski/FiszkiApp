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
                    )
                            .addCallback(object : RoomDatabase.Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    //pre-populate data
                                    Executors.newSingleThreadExecutor().execute {
                                        instance?.let {
                                            it.fiszkiDatabaseDao.insertLanguage(Language(0, "polish", "pl"))
                                            it.fiszkiDatabaseDao.insertLanguage(Language(1, "english", "en"))
                                            it.fiszkiDatabaseDao.insertLanguage(Language(2, "russian", "ru"))

                                            it.fiszkiDatabaseDao.insertLangToLang(LangToLang(0, 0, 1))
                                            it.fiszkiDatabaseDao.insertLangToLang(LangToLang(1, 0, 2))
                                            it.fiszkiDatabaseDao.insertLangToLang(LangToLang(2, 1, 0))
                                            it.fiszkiDatabaseDao.insertLangToLang(LangToLang(3, 1, 2))
                                            it.fiszkiDatabaseDao.insertLangToLang(LangToLang(4, 2, 0))
                                            it.fiszkiDatabaseDao.insertLangToLang(LangToLang(5, 2, 1))
                                        }
                                    }
                                }
                            }
                            ).fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }

}