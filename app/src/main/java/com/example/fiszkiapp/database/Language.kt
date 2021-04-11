package com.example.fiszkiapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "language_table")
data class Language(

        @PrimaryKey
        var languageId: Int,
        val name: String = "",
        var shortName: String = ""
)