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
        dataSource.insertTopic(Topic(1, "Kuchnia", 0))
        dataSource.insertTopic(Topic(2, "Dom", 0))
        dataSource.insertTopic(Topic(3, "Praca", 0))
        dataSource.insertTopic(Topic(4, "IT", 0))
        dataSource.insertTopic(Topic(5, "Rolnictwo", 1))
        dataSource.insertTopic(Topic(6, "Fabryka", 1))
        dataSource.insertTopic(Topic(7, "Górnictwo", 1))
        dataSource.insertTopic(Topic(8, "Podróże", 1))


        //Log.i("fff", x[0].topics[0].topicName)
        }


}