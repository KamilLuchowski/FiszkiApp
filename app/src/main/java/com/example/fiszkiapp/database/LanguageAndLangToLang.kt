package com.example.fiszkiapp.database

import androidx.room.Embedded
import androidx.room.Relation

data class LanguageAndLangToLang(
    @Embedded val l2l: LangToLang,
    @Relation(
                parentColumn = "firstLanguage",
                entityColumn = "languageId"
        )
        val language1: Language,
    @Relation(
                parentColumn = "secondLanguage",
                entityColumn = "languageId"
        )
        val language2: Language
)