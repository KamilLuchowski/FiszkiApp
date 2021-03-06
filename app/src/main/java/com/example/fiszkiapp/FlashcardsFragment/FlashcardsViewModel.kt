package com.example.fiszkiapp.FlashcardsFragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fiszkiapp.database.FiszkiDatabaseDao
import com.example.fiszkiapp.database.Flashcard
import com.example.fiszkiapp.database.Topic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FlashcardsViewModel(val dataSource: FiszkiDatabaseDao, application: Application, val topicId: Int): ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private var _flashcards: LiveData<List<Flashcard>> = dataSource.getFlashcards(topicId)
    val flashcards
        get() = _flashcards

    private var _topic: LiveData<Topic> = dataSource.getTopic(topicId)
    val topic
        get() = _topic

    private var _topicCounter: LiveData<Int> = dataSource.flashcardsInTopicCount(topicId)
    val topicCounter
        get() = _topicCounter

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        uiScope.launch {
        }
    }

    fun deleteTopic(topicId: Int){
        CoroutineScope(IO).launch {
            dataSource.deleteTopicFlashcards(topicId)
            dataSource.deleteTopic(topicId)
        }
    }

    fun renameTopic(topicId: Int, str: String) {
        CoroutineScope(IO).launch {
            dataSource.updateTopic(topicId, str)
        }

    }
}
