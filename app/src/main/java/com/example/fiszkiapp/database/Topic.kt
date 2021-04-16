package com.example.fiszkiapp.database

import androidx.room.*

@Entity(tableName = "topic_table")
data class Topic(
    val topicName: String,
    var topicLang2Lang: Int
) {
    @PrimaryKey (autoGenerate = true)
    var topicId: Int = 0
}
