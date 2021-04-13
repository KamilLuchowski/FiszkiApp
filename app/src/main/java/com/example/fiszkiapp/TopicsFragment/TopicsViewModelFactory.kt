package com.example.fiszkiapp.TopicsFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fiszkiapp.TitleFragment.TitleViewModel
import com.example.fiszkiapp.database.FiszkiDatabaseDao

class TopicsViewModelFactory(
    private val dataSource: FiszkiDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("unchecked_cast")
        if (modelClass.isAssignableFrom(TopicsViewModel::class.java)) {
            return TopicsViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
