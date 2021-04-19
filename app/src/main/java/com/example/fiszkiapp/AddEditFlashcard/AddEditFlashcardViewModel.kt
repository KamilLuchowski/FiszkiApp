package com.example.fiszkiapp.AddEditFlashcard

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fiszkiapp.database.FiszkiDatabaseDao
import com.example.fiszkiapp.database.Flashcard
import com.example.fiszkiapp.database.FlashcardAndTopic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AddEditFlashcardViewModel(val dataSource: FiszkiDatabaseDao, application: Application, val flashcardId: Int, val topicId: Int): ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private var _flashcardAndTopic: LiveData<FlashcardAndTopic>? = null
    val flashcardAndTopic
        get() = _flashcardAndTopic

    private var _flashcard: LiveData<Flashcard>? = null
    val flashcard
        get() = _flashcard

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        if(flashcardId == -1){
            //new flashcard
        }
        else{
            _flashcardAndTopic = dataSource.getFlashcardandTopic(flashcardId)
            _flashcard = dataSource.getFlashcard(flashcardId)
            uiScope.launch {
            }
        }
    }

    fun deleteFlashcard(flashcardId: Int){
        CoroutineScope(Dispatchers.IO).launch {
            dataSource.deleteFlashcard(flashcardId)
        }
    }

    fun addFlashcard(wordText: String, translateText: String) {
        CoroutineScope(IO).launch {
            dataSource.insertFlashcard(Flashcard(wordText, translateText, topicId))
        }
    }

    fun updateFlashcard(flashcardId: Int, wordText: String, translateText: String) {
        CoroutineScope(IO).launch {
            dataSource.updateFlashcard(flashcardId, wordText, translateText)
        }
    }
}