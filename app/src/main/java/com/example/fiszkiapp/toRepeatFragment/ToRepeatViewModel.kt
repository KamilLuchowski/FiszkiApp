package com.example.fiszkiapp.toRepeatFragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.fiszkiapp.database.FiszkiDatabaseDao
import com.example.fiszkiapp.database.Flashcard
import com.example.fiszkiapp.database.ToRepeat
import com.example.fiszkiapp.database.Topic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.*

class ToRepeatViewModel(val dataSource: FiszkiDatabaseDao, application: Application, langToLangId: Int): ViewModel() {

    private var viewModelJob = Job()

    var _flashcards: LiveData<List<Flashcard>>
    val flashcards
        get() = _flashcards


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {

        _flashcards = dataSource.gegege(langToLangId)

    }
}
