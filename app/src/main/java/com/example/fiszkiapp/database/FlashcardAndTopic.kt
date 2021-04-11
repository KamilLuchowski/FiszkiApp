package com.example.fiszkiapp.database

import androidx.room.Embedded
import androidx.room.Relation

data class FlashcardAndTopic(
    @Embedded val topic: Topic,
    @Relation(
        parentColumn = "topicId",
        entityColumn = "flashcardId"
    )
    val flashcards: List<Flashcard>
)