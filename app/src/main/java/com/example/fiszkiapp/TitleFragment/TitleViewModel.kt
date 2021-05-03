package com.example.fiszkiapp.TitleFragment

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fiszkiapp.database.FiszkiDatabaseDao
import com.example.fiszkiapp.database.Flashcard
import com.example.fiszkiapp.database.LanguageAndLangToLang
import com.example.fiszkiapp.database.Topic
import kotlinx.coroutines.*


class TitleViewModel(val dataSource: FiszkiDatabaseDao, application: Application) : ViewModel() {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    var _languageAndLangToLang: LiveData<List<LanguageAndLangToLang>>
    val languageAndLangToLang
        get() = _languageAndLangToLang

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        getDataFromDatabase()
        _languageAndLangToLang = dataSource.getLanguageAndLangToLang()
    }

    private fun getDataFromDatabase() {
        uiScope.launch {
            func()
        }
    }

    private suspend fun func() {
        dataSource.getLanguageAndLangToLang()
        dataSource.insertTopic(Topic( "Podróże", 0))
        dataSource.insertTopic(Topic( "Kuchnia", 0))
        dataSource.insertTopic(Topic( "Dom", 0))
        dataSource.insertTopic(Topic( "Praca", 0))
        dataSource.insertTopic(Topic( "IT", 0))
        dataSource.insertTopic(Topic( "Rolnictwo", 1))
        dataSource.insertTopic(Topic( "Fabryka", 1))
        dataSource.insertTopic(Topic("Górnictwo", 1))
        dataSource.insertTopic(Topic( "Podróże", 1))


        dataSource.insertFlashcard(Flashcard("Hello", "Cześć", 2))
        dataSource.insertFlashcard(Flashcard("Hello2", "Cześć2", 4))
        dataSource.insertFlashcard(Flashcard("Hello3", "Cześć3", 4))
        dataSource.insertFlashcard(Flashcard("Hello4", "Cześć4", 4))
        dataSource.insertFlashcard(Flashcard("Hello5", "Cześć5", 2))
        }

}