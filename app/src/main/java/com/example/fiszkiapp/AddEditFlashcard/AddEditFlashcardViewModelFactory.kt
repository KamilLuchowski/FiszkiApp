package com.example.fiszkiapp.AddEditFlashcard

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fiszkiapp.FlashcardsFragment.FlashcardsViewModel
import com.example.fiszkiapp.database.FiszkiDatabaseDao

class AddEditFlashcardViewModelFactory(
    private val dataSource: FiszkiDatabaseDao,
    private val application: Application,
    private val flashcardId: Int,
    private val topicId: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("unchecked_cast")
        if (modelClass.isAssignableFrom(AddEditFlashcardViewModel::class.java)) {
            return AddEditFlashcardViewModel(dataSource, application, flashcardId, topicId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}