package com.example.fiszkiapp.toRepeatFragment

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fiszkiapp.database.FiszkiDatabaseDao
import com.example.fiszkiapp.database.Flashcard
import kotlinx.coroutines.Job

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
        _flashcards = dataSource.getToRepeatFlashcards(langToLangId)

    }
}
