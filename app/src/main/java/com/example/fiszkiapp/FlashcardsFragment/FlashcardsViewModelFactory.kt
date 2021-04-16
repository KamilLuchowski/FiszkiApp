package com.example.fiszkiapp.FlashcardsFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fiszkiapp.TitleFragment.TitleViewModel
import com.example.fiszkiapp.database.FiszkiDatabaseDao

class FlashcardsViewModelFactory(
    private val dataSource: FiszkiDatabaseDao,
    private val application: Application,
    private val topicId: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("unchecked_cast")
        if (modelClass.isAssignableFrom(FlashcardsViewModel::class.java)) {
            return FlashcardsViewModel(dataSource, application, topicId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}