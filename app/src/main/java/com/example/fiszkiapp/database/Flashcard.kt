package com.example.fiszkiapp.database

import androidx.room.*

@Entity(tableName = "flashcard_table")
data class Flashcard(

    @PrimaryKey
    var flashcardId: Int,
    var word: String,
    var transaction: String,
    var flashcardTopic: Int
)