package com.example.fiszkiapp.toRepeatFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fiszkiapp.database.FiszkiDatabaseDao

class ToRepeatViewModelFactory(
    private val dataSource: FiszkiDatabaseDao,
    private val application: Application,
    private val langToLangId: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("unchecked_cast")
        if (modelClass.isAssignableFrom(ToRepeatViewModel::class.java)) {
            return ToRepeatViewModel(dataSource, application, langToLangId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}