package com.example.fiszkiapp.FlashcardDetails

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fiszkiapp.database.FiszkiDatabaseDao
import com.example.fiszkiapp.database.Flashcard
import com.example.fiszkiapp.database.FlashcardAndTopic
import com.example.fiszkiapp.database.Topic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FlashcardDetailsViewModel(val dataSource: FiszkiDatabaseDao, application: Application, val flashcardId: Int): ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private var _flashcardAndTopic: LiveData<FlashcardAndTopic>
    val flashcardAndTopic
        get() = _flashcardAndTopic

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        _flashcardAndTopic = dataSource.getFlashcardandTopic(flashcardId)
        uiScope.launch {
        }
    }

    fun deleteFlashcard(key: Int){
       uiScope.launch {
           dataSource.deleteFlashcard(key)
       }
    }
}