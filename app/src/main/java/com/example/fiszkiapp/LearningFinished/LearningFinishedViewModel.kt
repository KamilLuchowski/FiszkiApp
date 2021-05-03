package com.example.fiszkiapp.LearningFinished

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fiszkiapp.database.FiszkiDatabaseDao
import com.example.fiszkiapp.database.Flashcard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class LearningFinishedViewModel(val dataSource: FiszkiDatabaseDao, var application: Application): ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {

    }

}