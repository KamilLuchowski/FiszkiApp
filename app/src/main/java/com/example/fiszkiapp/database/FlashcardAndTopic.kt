package com.example.fiszkiapp.database

import androidx.room.Embedded
import androidx.room.Relation

data class FlashcardAndTopic(
    @Embedded val flashcard: Flashcard,
    @Relation(
        parentColumn = "flashcardTopic",
        entityColumn = "topicId"
    )
    val topic: Topic
)