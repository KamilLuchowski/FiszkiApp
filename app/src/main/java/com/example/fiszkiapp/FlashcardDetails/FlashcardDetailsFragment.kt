package com.example.fiszkiapp.FlashcardDetails

import android.app.AlertDialog
import android.content.ClipData
import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.*
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fiszkiapp.FlashcardsFragment.*
import com.example.fiszkiapp.LangToLangDictionary
import com.example.fiszkiapp.R
import com.example.fiszkiapp.database.FiszkiDatabase
import com.example.fiszkiapp.database.Flashcard
import com.example.fiszkiapp.database.FlashcardAndTopic
import com.example.fiszkiapp.database.Topic
import com.example.fiszkiapp.databinding.FragmentFlashcardDetailsBinding
import com.example.fiszkiapp.databinding.FragmentFlashcardsBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList


class FlashcardDetailsFragment: Fragment() {

    private lateinit var viewModel : FlashcardDetailsViewModel
    private lateinit var viewModelFactory: FlashcardDetailsViewModelFactory
    private lateinit var TTS: TextToSpeech

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val dataSource = FiszkiDatabase.getInstance(application).fiszkiDatabaseDao

        val flashcardId = FlashcardDetailsFragmentArgs.fromBundle(arguments!!).flashcardId
        val langtoLangId = FlashcardDetailsFragmentArgs.fromBundle(arguments!!).langToLang

        viewModelFactory = FlashcardDetailsViewModelFactory(dataSource, application, flashcardId, langtoLangId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FlashcardDetailsViewModel::class.java)


        val binding : FragmentFlashcardDetailsBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_flashcard_details, container, false)
        var flashcardAndTopic: FlashcardAndTopic? = null

        viewModel.flashcardAndTopic.observe(viewLifecycleOwner, Observer {
            flashcardAndTopic = it
            binding.wordDetails.text = it.flashcard.word
            binding.translationDetails.text = it.flashcard.translation
            binding.detailsDescription.text = it.flashcard.description
            textToSpeech(it.flashcard.word, langtoLangId, binding.detailsSpeakerButton)
        })

        binding.editFlashcardFab.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(FlashcardDetailsFragmentDirections
                .actionFlashcardDetailsFragmentToAddEditFlashcardFragment(
                    flashcardAndTopic?.flashcard?.flashcardId!!,
                    flashcardAndTopic?.topic?.topicId!!,
                    langtoLangId
            ))
        }

        binding.deleteFlashcardFab.setOnClickListener {
            viewModel.deleteFlashcard(flashcardId)
            Navigation.findNavController(it).navigateUp()
        }

        val list = ArrayList<String>()
        val dropdown: Spinner = binding.spinner2

        viewModel._langToLangAndTopic.observe(viewLifecycleOwner, Observer {
            it.forEach {
                list.add(it.topicName)
            }

            val t = it.filter { topic -> topic.topicId == flashcardAndTopic?.topic?.topicId!! }.first()


            val aa = ArrayAdapter(application.applicationContext,
                    android.R.layout.simple_spinner_dropdown_item, list)
                dropdown.adapter = aa
                dropdown.setSelection(it.indexOf(t))
        })

        dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.updateTopic(flashcardId, dropdown)
            }
        }


        return binding.root
    }


    fun textToSpeech(word:String, langToLangId: Int, speakerButton: FloatingActionButton){
        TTS = TextToSpeech(context,
            TextToSpeech.OnInitListener { status ->
                if (status != TextToSpeech.ERROR) {
                    val dict = LangToLangDictionary()
                    TTS.language = Locale(dict.learningLanguage(langToLangId))
                    //                    t1.setLanguage(Locale.GERMANY);
                    //                    t1.setLanguage(new Locale("ru"));
                    Log.d("TTS", "ok")
                } else {
                    Log.d("TTS", "error")
                }
            })
        speakerButton.setOnClickListener{
            TTS.speak(word, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }
}