package com.example.fiszkiapp.database

import androidx.room.*

@Entity(tableName = "langToLang_table")
class LangToLang(
        @PrimaryKey
        var langToLangId: Int,
        val firstLanguage: Int,
        val secondLanguage: Int
)

