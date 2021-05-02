package com.example.fiszkiapp.LearningFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fiszkiapp.database.FiszkiDatabaseDao

class LearningViewModelFactory(
    private val dataSource: FiszkiDatabaseDao,
    private val application: Application,
    private val topicId: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("unchecked_cast")
        if (modelClass.isAssignableFrom(LearningViewModel::class.java)) {
            return LearningViewModel(dataSource, application, topicId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}