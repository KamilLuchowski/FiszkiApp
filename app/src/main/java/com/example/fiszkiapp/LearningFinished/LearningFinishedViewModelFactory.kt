package com.example.fiszkiapp.LearningFinished

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fiszkiapp.database.FiszkiDatabaseDao

class LearningFinishedViewModelFactory(
    private val dataSource: FiszkiDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("unchecked_cast")
        if (modelClass.isAssignableFrom(LearningFinishedViewModel::class.java)) {
            return LearningFinishedViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}