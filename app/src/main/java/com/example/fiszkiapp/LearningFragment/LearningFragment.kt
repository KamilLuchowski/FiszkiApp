package com.example.fiszkiapp.LearningFragment

import android.opengl.Visibility
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.*
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.fiszkiapp.LangToLangDictionary
import com.example.fiszkiapp.R
import com.example.fiszkiapp.database.FiszkiDatabase
import com.example.fiszkiapp.databinding.FragmentLearningBinding
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import java.util.*


class LearningFragment : Fragment() {

    private lateinit var viewModel: LearningViewModel
    private lateinit var viewModelFactory: LearningViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val dataSource = FiszkiDatabase.getInstance(application).fiszkiDatabaseDao

        val topicId = LearningFragmentArgs.fromBundle(arguments!!).topicId
        val langToLangId = LearningFragmentArgs.fromBundle(arguments!!).langToLang

        viewModelFactory = LearningViewModelFactory(dataSource, application, topicId, langToLangId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LearningViewModel::class.java)

        val binding: FragmentLearningBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_learning, container, false)

        viewModel.flashcards.observe(viewLifecycleOwner, Observer {
            viewModel.randomList()
            bind(inflater, container, binding)
            textToSpeech(viewModel.randomFlashcards?.get(viewModel.iterator)!!.word, langToLangId, binding.learningSpeakerButton)
        })

        binding.showAnswerButton.setOnClickListener {
            if (binding.learningTranslation.visibility == INVISIBLE)
                translationVisible(binding)
            else
                translationInvisible(binding)
        }

        binding.iDidntKnowButton.setOnClickListener {
            translationInvisible(binding)
            viewModel.ididntknowClicks++
            viewModel.didntKnowHandler(langToLangId, inflater, container, binding)
            if (viewModel.iterator < viewModel.flashcardsSize) {
                bind(inflater, container, binding)
            } else {
                viewModel.iterations++
                viewModel.iterator = 0
                bind(inflater, container, binding)
            }
        }

        binding.iKnewItButton.setOnClickListener {
            translationInvisible(binding)
            viewModel.iknewitClicks++
            viewModel.iKnewHandler(topicId)

            if (viewModel.flashcardsSize == 0) {
                Navigation.findNavController(it)
                    .navigate(LearningFragmentDirections.actionLearningFragmentToLearningFinishedFragment(viewModel.iknewitClicks, viewModel.ididntknowClicks))
                return@setOnClickListener
            }
            if (viewModel.iterator == viewModel.flashcardsSize)
                viewModel.iterator = 0

            bind(inflater, container, binding)
        }
        return binding.root
    }

    private fun translationVisible(binding: FragmentLearningBinding) {
        binding.learningTranslation.visibility = VISIBLE
        binding.showAnswerButton.text = getString(R.string.hide_answer)
    }

    private fun translationInvisible(binding: FragmentLearningBinding) {
        binding.learningTranslation.visibility = INVISIBLE
        binding.showAnswerButton.text = getString(R.string.show_answer)
    }

    private fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: FragmentLearningBinding
    ) {
        binding.learningWord.text = viewModel.randomFlashcards?.get(viewModel.iterator)?.word
        binding.learningTranslation.text =
            viewModel.randomFlashcards?.get(viewModel.iterator)?.translation
    }

    private fun textToSpeech(word:String, langToLangId: Int, speakerButton: ExtendedFloatingActionButton){
        val TTS:TextToSpeech = viewModel.textToSpeechInit()
        val dict = LangToLangDictionary()
        TTS.language = Locale(dict.learningLanguage(langToLangId))
        speakerButton.setOnClickListener{
            TTS.speak(word, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }


}