package com.example.fiszkiapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FiszkiDatabaseDao {

    @Query("SELECT count(*) FROM langToLang_table")
    fun langToLangCount(): Int

    @Query("SELECT count(*) FROM flashcard_table where flashcardTopic=:key")
    fun flashcardsInTopicCount(key: Int): LiveData<Int>

    @Insert
    fun insertLanguage(lang: Language)

    @Insert
    fun insertLangToLang(lang: LangToLang)

    @Query("SELECT * from language_table WHERE languageId =:key")
     suspend fun getLanguage(key: Int): Language?

    @Query("SELECT * FROM langToLang_table")
    fun getLangToLangs(): LiveData<List<LangToLang>>

    @Transaction
    @Query("SELECT * FROM langToLang_table")
    fun getLanguageAndLangToLang(): LiveData<List<LanguageAndLangToLang>>

    @Transaction
    @Query("SELECT * FROM topic_table WHERE topicLang2Lang =:key")
    fun getTopics(key: Int): LiveData<List<Topic>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopic(topic: Topic)

    @Transaction
    @Query("SELECT * FROM flashcard_table WHERE flashcardTopic =:key")
    fun getFlashcards(key: Int): LiveData<List<Flashcard>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashcard(flashcard: Flashcard)

    @Query("DELETE FROM flashcard_table WHERE flashcardTopic =:key")
    suspend fun deleteTopicFlashcards(key: Int)

    @Query("DELETE FROM topic_table WHERE topicId=:key")
    suspend fun deleteTopic(key: Int)

    @Query("SELECT * FROM flashcard_table WHERE flashcardId =:key LIMIT 1")
    fun getFlashcard(key: Int): LiveData<Flashcard>

    @Query("DELETE FROM flashcard_table WHERE flashcardId=:key")
    fun deleteFlashcard(key: Int)

    @Query("UPDATE topic_table SET topicName=:newName WHERE topicId=:key")
    suspend fun updateTopic(key: Int, newName: String)

    @Transaction
    @Query("SELECT * FROM flashcard_table WHERE flashcardId=:key")
    fun getFlashcardandTopic(key: Int): LiveData<FlashcardAndTopic>

    @Transaction
    @Query(
        "UPDATE flashcard_table SET word=:newWord, translation=:newTranslation WHERE flashcardId=:key")
     suspend fun updateFlashcard(key: Int, newWord: String, newTranslation: String)

    @Transaction
    @Query(
        "UPDATE flashcard_table SET flashcardTopic=:topicId WHERE flashcardId=:key")
    suspend fun updateFlashcardTopic(key: Int, topicId: Int)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashcardToRepeat(toRepeat: ToRepeat)

    @Transaction
    @Query("SELECT * FROM torepeat_table WHERE langToLangId =:langToLangId")
    fun getFlashcardFromToRepeat(langToLangId: Int): LiveData<List<ToRepeat>>


    @Query("DELETE FROM torepeat_table WHERE flashcardId=:key")
    fun deleteToRepeatFlashcard(key: Int)

    @Transaction
    @Query("SELECT * FROM flashcard_table WHERE flashcardId IN (SELECT flashcardId from torepeat_table WHERE langToLangId =:langToLangId)")
    fun getToRepeatFlashcards(langToLangId: Int): LiveData<List<Flashcard>>

    @Query("SELECT * FROM topic_table WHERE topicId=:key")
    fun getTopic(key: Int): LiveData<Topic>
}


