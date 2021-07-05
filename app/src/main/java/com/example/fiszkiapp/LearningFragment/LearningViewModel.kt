package com.example.fiszkiapp.LearningFragment

import android.app.Application
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fiszkiapp.database.FiszkiDatabaseDao
import com.example.fiszkiapp.database.Flashcard
import com.example.fiszkiapp.database.ToRepeat
import com.example.fiszkiapp.databinding.FragmentLearningBinding
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
    lateinit var TTS: TextToSpeech
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

    var iknewitClicks: Int = 0
    var ididntknowClicks: Int = 0


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        if (topicId == -1) {
            _flashcards = dataSource.getToRepeatFlashcards(langToLangId)
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

    fun didntKnowHandler(
        langToLangId: Int,
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: FragmentLearningBinding
    ) {
        if (iterator < flashcardsSize && iterations == 2) {
            _randomFlashcards?.get(iterator)?.let { it1 ->
                addToRepeat(
                    it1, langToLangId
                )
            }
        }

        iterator++

    }

    fun iKnewHandler(topicId: Int) {
        if (topicId == -1)
            deleteToRepeatFlashcard(_randomFlashcards?.get(iterator)?.flashcardId)
        _randomFlashcards?.removeAt(iterator)

        flashcardsSize = randomFlashcards?.size!!
    }

    fun textToSpeechInit(): TextToSpeech {
        return TextToSpeech(application.applicationContext,
            TextToSpeech.OnInitListener { status ->
                if (status != TextToSpeech.ERROR) {

                    //                    t1.setLanguage(Locale.GERMANY);
                    //                    t1.setLanguage(new Locale("ru"));
                    Log.d("TTS", "ok")
                } else {
                    Log.d("TTS", "error")
                }
            })
    }
}