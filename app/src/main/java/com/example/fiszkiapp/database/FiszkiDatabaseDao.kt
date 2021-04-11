package com.example.fiszkiapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FiszkiDatabaseDao {

    @Insert
    fun insertLanguage(lang: Language)

    @Insert
    fun insertLangToLang(lang: LangToLang)

    @Query("SELECT * from language_table WHERE languageId =:key")
     suspend fun getLanguage(key: Int): Language?

    @Query("DELETE FROM language_table")
     suspend fun clearLang()

    @Query("DELETE FROM langToLang_table")
    suspend fun clearLangToLang()
//
//    @Query("SELECT * from langToLang_table")
//    suspend fun getLangToLangs(): List<LangToLang>

    @Query("SELECT * FROM langToLang_table")
    fun getAllNights(): LiveData<List<LangToLang>>

    @Transaction
    @Query("SELECT * FROM langToLang_table")
    fun getLang2Langs(): LiveData<List<LanguageAndLangToLang>>

    @Transaction
    @Query("SELECT * FROM langToLang_table")
    suspend fun getUsersWithPlaylists(): List<LangToLangAndTopic>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopic(topic: Topic)
}


