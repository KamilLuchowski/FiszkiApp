package com.example.fiszkiapp.LearningFragment

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.fiszkiapp.R
import com.example.fiszkiapp.database.FiszkiDatabase
import com.example.fiszkiapp.databinding.FragmentLearningBinding


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
        })

        binding.showAnswerButton.setOnClickListener {
            translationVisible(binding)
        }



        binding.iDidntKnowButton.setOnClickListener {
            translationInvisible(binding)

            if (viewModel.iterator < viewModel.flashcardsSize && viewModel.iterations == 2) {
                viewModel._randomFlashcards?.get(viewModel.iterator)?.let { it1 ->
                    viewModel.addToRepeat(
                        it1, langToLangId
                    )
                }
            }

            viewModel.iterator++
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
            if (topicId == -1)
                viewModel.deleteToRepeatFlashcard(viewModel._randomFlashcards?.get(viewModel.iterator)?.flashcardId)
            viewModel._randomFlashcards?.removeAt(viewModel.iterator)

            viewModel.flashcardsSize = viewModel.randomFlashcards?.size!!

            if (viewModel.flashcardsSize == 0) {
                Navigation.findNavController(it)
                    .navigate(LearningFragmentDirections.actionLearningFragmentToLearningFinishedFragment())
                return@setOnClickListener
            }
            if (viewModel.iterator == viewModel.flashcardsSize)
                viewModel.iterator = 0

            bind(inflater, container, binding)
        }

        return binding.root
    }

    private fun translationVisible(binding: FragmentLearningBinding) {
        binding.learningTranslation.visibility = View.VISIBLE
        binding.floatingActionButton.visibility = View.VISIBLE
    }
    private fun translationInvisible(binding: FragmentLearningBinding) {
        binding.learningTranslation.visibility = View.INVISIBLE
        binding.floatingActionButton.visibility = View.INVISIBLE
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
}