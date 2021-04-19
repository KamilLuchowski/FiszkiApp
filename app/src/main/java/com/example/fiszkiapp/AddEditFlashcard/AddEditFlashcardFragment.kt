package com.example.fiszkiapp.AddEditFlashcard

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.fiszkiapp.R
import com.example.fiszkiapp.database.FiszkiDatabase
import com.example.fiszkiapp.database.Flashcard
import com.example.fiszkiapp.database.FlashcardAndTopic
import com.example.fiszkiapp.databinding.FragmentAddEditFlashcardBinding

class AddEditFlashcardFragment: Fragment() {

    private lateinit var viewModel : AddEditFlashcardViewModel
    private lateinit var viewModelFactory: AddEditFlashcardViewModelFactory

    var flashcardId: Int = 0
    var topicId: Int = 0
    var flashcard: FlashcardAndTopic? = null//Flashcard("","", 0)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val dataSource = FiszkiDatabase.getInstance(application).fiszkiDatabaseDao

        flashcardId = AddEditFlashcardFragmentArgs.fromBundle(arguments!!).flashcardId
        topicId = AddEditFlashcardFragmentArgs.fromBundle(arguments!!).topicId


        viewModelFactory = AddEditFlashcardViewModelFactory(dataSource, application, flashcardId, topicId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddEditFlashcardViewModel::class.java)

        val binding : FragmentAddEditFlashcardBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_edit_flashcard, container, false)

        //binding.textView.text = viewModel.flashcard.value?.word

        viewModel.flashcardAndTopic?.observe(viewLifecycleOwner, Observer {
            flashcard = it
            binding.wordTextEdit.setText(it.flashcard.word)
            binding.translationTextEdit.setText(it.flashcard.translation)
        })

        binding.addEditDoneFloatingButton.setOnClickListener {
            val wordText = binding.wordTextEdit.text.toString()
            val translateText = binding.translationTextEdit.text.toString()
            if(wordText.length == 0 || translateText.length == 0)
                Toast.makeText(context, getString(R.string.empty_field), Toast.LENGTH_SHORT).show()
            else{
                if (flashcardId == -1)
                    viewModel.addFlashcard(wordText, translateText)
                else {
                    viewModel.updateFlashcard(flashcardId, wordText, translateText)
                }
                activity?.currentFocus?.let { view ->
                    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
                Navigation.findNavController(it).navigateUp()

            }
        }

        return binding.root
    }

}