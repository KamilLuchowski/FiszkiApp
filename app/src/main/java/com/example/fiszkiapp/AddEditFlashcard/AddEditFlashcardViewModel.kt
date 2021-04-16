package com.example.fiszkiapp.AddEditFlashcard

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fiszkiapp.database.FiszkiDatabaseDao
import com.example.fiszkiapp.database.Flashcard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AddEditFlashcardViewModel(val dataSource: FiszkiDatabaseDao, application: Application, val flashcardId: Int, val topicId: Int): ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private var _flashcard: LiveData<Flashcard>? = null
    val flashcard
        get() = _flashcard

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        if(flashcardId == -1){
            Toast
                .makeText(application.applicationContext, "Dupaaaaaa", Toast.LENGTH_SHORT)
                .show()
            //new flashcard
        }
        else{
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
}