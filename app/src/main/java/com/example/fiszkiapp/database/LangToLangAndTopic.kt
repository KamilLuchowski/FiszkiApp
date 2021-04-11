package com.example.fiszkiapp.database

import androidx.room.Embedded
import androidx.room.Relation

data class LangToLangAndTopic(
    @Embedded val lang2Lang: LangToLang,
    @Relation(
        parentColumn = "langToLangId",
        entityColumn = "topicLang2Lang"
    )
    val topics: List<Topic>
)