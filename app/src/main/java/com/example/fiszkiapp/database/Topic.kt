package com.example.fiszkiapp.database

import androidx.room.*

@Entity(tableName = "topic_table")
data class Topic(
    @PrimaryKey
    var topicId: Int,
    val topicName: String,
    var topicLang2Lang: Int
)

