package com.example.fiszkiapp.TopicsFragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fiszkiapp.database.FiszkiDatabaseDao
import com.example.fiszkiapp.database.LangToLangAndTopic
import com.example.fiszkiapp.database.Topic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TopicsViewModel(val dataSource: FiszkiDatabaseDao, application: Application): ViewModel(){

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    var langToLangId: Int = 0

    private lateinit var _langToLangAndTopic: LiveData<List<Topic>>
    val langToLangAndTopic
        get() = _langToLangAndTopic

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        uiScope.launch {
            func()
        }
    }

    private suspend fun func() {
        Log.i("asdi", langToLangId.toString())
        _langToLangAndTopic = dataSource.getUsersWithPlaylists(langToLangId)
        _langToLangAndTopic.value?.forEach {
            Log.i("asd", _langToLangAndTopic.toString())
        }
    }

}