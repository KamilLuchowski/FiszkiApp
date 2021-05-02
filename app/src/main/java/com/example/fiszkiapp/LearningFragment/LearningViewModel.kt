package com.example.fiszkiapp.LearningFragment

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fiszkiapp.database.FiszkiDatabaseDao
import com.example.fiszkiapp.database.Flashcard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LearningViewModel(val dataSource: FiszkiDatabaseDao, var application: Application, val topicId: Int): ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    var _flashcards: LiveData<List<Flashcard>>
    val flashcards
        get() = _flashcards

    var _randFlashcards: List<Flashcard>? = null
    val randFlashcards
        get() = _flashcards

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        _flashcards = dataSource.getFlashcards(topicId)
        //Log.i("RAND", _flashcards.value.toString())
    }

    fun randList(){
        if (_flashcards.value == null){
            Log.i("RAND","Null/No data")
            return
        }
        else if (_flashcards.value!!.size > 10)
            _randFlashcards = _flashcards.value!!.shuffled().take(10)
        else
            _randFlashcards = _flashcards.value!!.shuffled().take(_flashcards.value!!.size)

        Log.i("RAND", _randFlashcards.toString())
    }
}