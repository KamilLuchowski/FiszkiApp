package com.example.fiszkiapp.TitleFragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.fiszkiapp.database.FiszkiDatabaseDao
import com.example.fiszkiapp.database.Topic
import kotlinx.coroutines.*


class TitleViewModel(val dataSource: FiszkiDatabaseDao, application: Application) : ViewModel() {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private val _nights = dataSource.getLang2Langs()
    val nights
        get() = _nights



    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        getDataFromDatabase()
    }

    private fun getDataFromDatabase() {
        uiScope.launch {
            func()
        }
    }

    private suspend fun func() {
        dataSource.insertTopic(Topic(0, "Podróże", 0))
        val x = dataSource.getUsersWithPlaylists()
        x.forEach {
            Log.i("asd", x.toString())
        }

        //Log.i("fff", x[0].topics[0].topicName)
        }


}