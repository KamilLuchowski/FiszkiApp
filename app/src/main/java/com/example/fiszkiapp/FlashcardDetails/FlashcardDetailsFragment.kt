package com.example.fiszkiapp.FlashcardDetails

import android.app.AlertDialog
import android.content.ClipData
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
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
import com.example.fiszkiapp.R
import com.example.fiszkiapp.database.FiszkiDatabase
import com.example.fiszkiapp.database.Flashcard
import com.example.fiszkiapp.database.FlashcardAndTopic
import com.example.fiszkiapp.databinding.FragmentFlashcardDetailsBinding
import com.example.fiszkiapp.databinding.FragmentFlashcardsBinding


class FlashcardDetailsFragment: Fragment() {

    private lateinit var viewModel : FlashcardDetailsViewModel
    private lateinit var viewModelFactory: FlashcardDetailsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val dataSource = FiszkiDatabase.getInstance(application).fiszkiDatabaseDao

        val flashcardId = FlashcardDetailsFragmentArgs.fromBundle(arguments!!).flashcardId

        viewModelFactory = FlashcardDetailsViewModelFactory(dataSource, application, flashcardId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FlashcardDetailsViewModel::class.java)

        val binding : FragmentFlashcardDetailsBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_flashcard_details, container, false)

        var flashcardAndTopic: FlashcardAndTopic? = null

        viewModel.flashcardAndTopic.observe(viewLifecycleOwner, Observer {
            flashcardAndTopic = it
            binding.wordDetails.text = it.flashcard.word.toString()
            binding.translationDetails.text = it.flashcard.translation.toString()
            binding.topic.text = it.topic.topicName.toString()
        })

        binding.editFlashcardFab.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(FlashcardDetailsFragmentDirections
                .actionFlashcardDetailsFragmentToAddEditFlashcardFragment(
                flashcardAndTopic?.flashcard?.flashcardId!!,
                flashcardAndTopic?.topic?.topicId!!
            ))
        }

        binding.deleteFlashcardFab.setOnClickListener {
            viewModel.deleteFlashcard(flashcardId)
            Navigation.findNavController(it).navigateUp()
        }

        return binding.root
    }
}