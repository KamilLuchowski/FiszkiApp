package com.example.fiszkiapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "torepeat_table")
data class ToRepeat(
    @PrimaryKey
    var flashcardId: Int,
    val langToLangId: Int
)