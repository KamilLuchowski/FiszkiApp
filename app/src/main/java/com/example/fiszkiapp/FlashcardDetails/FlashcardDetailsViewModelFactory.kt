package com.example.fiszkiapp.FlashcardDetails

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fiszkiapp.FlashcardsFragment.FlashcardsViewModel
import com.example.fiszkiapp.TitleFragment.TitleViewModel
import com.example.fiszkiapp.database.FiszkiDatabaseDao

class FlashcardDetailsViewModelFactory(
    private val dataSource: FiszkiDatabaseDao,
    private val application: Application,
    private val flashcardId: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("unchecked_cast")
        if (modelClass.isAssignableFrom(FlashcardDetailsViewModel::class.java)) {
            return FlashcardDetailsViewModel(dataSource, application, flashcardId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}