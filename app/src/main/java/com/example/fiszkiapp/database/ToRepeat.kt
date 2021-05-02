package com.example.fiszkiapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "torepeat_table")
data class ToRepeat(
    val flashcardId: Int,
    val langToLangId: Int
){
    @PrimaryKey (autoGenerate = true)
    var toRepeatId: Int = 0
}