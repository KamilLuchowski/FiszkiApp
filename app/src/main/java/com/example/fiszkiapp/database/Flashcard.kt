package com.example.fiszkiapp.database

import androidx.room.*

@Entity(tableName = "flashcard_table")
data class Flashcard(
    var word: String,
    var translation: String,
    var flashcardTopic: Int
){
    @PrimaryKey (autoGenerate = true)
    var flashcardId: Int = 0
}