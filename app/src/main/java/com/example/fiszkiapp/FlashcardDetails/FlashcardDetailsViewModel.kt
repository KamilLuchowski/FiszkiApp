package com.example.fiszkiapp.FlashcardDetails

import android.app.Application
import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Spinner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fiszkiapp.LangToLangDictionary
import com.example.fiszkiapp.database.FiszkiDatabaseDao
import com.example.fiszkiapp.database.Flashcard
import com.example.fiszkiapp.database.FlashcardAndTopic
import com.example.fiszkiapp.database.Topic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.util.*

class FlashcardDetailsViewModel(val dataSource: FiszkiDatabaseDao, application: Application, val flashcardId: Int, langtoLangId: Int): ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)


    var _langToLangAndTopic: LiveData<List<Topic>>
    val langToLangAndTopic
        get() = _langToLangAndTopic

    private var _flashcardAndTopic: LiveData<FlashcardAndTopic>
    val flashcardAndTopic
        get() = _flashcardAndTopic

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        _flashcardAndTopic = dataSource.getFlashcardandTopic(flashcardId)
        _langToLangAndTopic = dataSource.getTopics(langtoLangId)
    }

    fun deleteFlashcard(key: Int){
       uiScope.launch {
           dataSource.deleteFlashcard(key)
           dataSource.deleteToRepeatFlashcard(key)
       }
    }

    fun updateTopic(key: Int, dropdown: Spinner){
        uiScope.launch {
            val newTopic = _langToLangAndTopic.value?.get(dropdown.selectedItemPosition)?.topicId
            if (newTopic != null) {
                dataSource.updateFlashcardTopic(flashcardId, newTopic)
            }
        }
    }


}