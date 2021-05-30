package com.example.fiszkiapp.LearningFragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fiszkiapp.database.FiszkiDatabaseDao
import com.example.fiszkiapp.database.Flashcard
import com.example.fiszkiapp.database.ToRepeat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LearningViewModel(
    val dataSource: FiszkiDatabaseDao,
    var application: Application,
    val topicId: Int,
    langToLangId: Int
): ViewModel() {

    private var viewModelJob = Job()

    private var _flashcards: LiveData<List<Flashcard>>
    val flashcards
        get() = _flashcards

    var _randomFlashcards: MutableList<Flashcard>? = null
    val randomFlashcards
        get() = _randomFlashcards

    var iterator: Int = 0
    var flashcardsSize: Int = 0
    var iterations = 0


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        if (topicId == -1) {
            _flashcards = dataSource.gegege(langToLangId)
        }
        else
            _flashcards = dataSource.getFlashcards(topicId)
        //Log.i("RAND", _flashcards.value.toString())
    }

    fun randomList(){
        if (_flashcards.value == null){
            Log.i("RAND","Null/No data")
            return
        }
        else if (_flashcards.value!!.size > 10)
            _randomFlashcards = _flashcards.value!!.shuffled().take(10).toMutableList()
        else
            _randomFlashcards = _flashcards.value!!.shuffled().take(_flashcards.value!!.size).toMutableList()

        flashcardsSize = randomFlashcards?.size!!

        Log.i("RAND", flashcardsSize.toString())
    }

    fun addToRepeat(flashcard: Flashcard, langToLangId: Int) {
        val f =  ToRepeat(flashcard.flashcardId, langToLangId)

        CoroutineScope(Dispatchers.IO).launch {
            dataSource.insertFlashcardToRepeat(f)
        }
    }

    fun deleteToRepeatFlashcard(flashcardId: Int?) {
        if (flashcardId != null) {
            CoroutineScope(IO).launch {
            dataSource.deleteToRepeatFlashcard(flashcardId)
            }
        }
    }
}