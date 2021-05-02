package com.example.fiszkiapp.LearningFragment

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fiszkiapp.R
import com.example.fiszkiapp.database.FiszkiDatabase
import com.example.fiszkiapp.databinding.FragmentLearningBinding


class LearningFragment: Fragment() {

    private lateinit var viewModel : LearningViewModel
    private lateinit var viewModelFactory: LearningViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val dataSource = FiszkiDatabase.getInstance(application).fiszkiDatabaseDao

        val topicId = LearningFragmentArgs.fromBundle(arguments!!).topicId

        viewModelFactory = LearningViewModelFactory(dataSource, application, topicId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LearningViewModel::class.java)

        val binding : FragmentLearningBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_learning, container, false)

        viewModel.flashcards.observe(viewLifecycleOwner, Observer {
            viewModel.randList()
        })

        binding.showAnswerButton.setOnClickListener {
            binding.learningTranslation.visibility = View.VISIBLE
            binding.floatingActionButton.visibility = View.VISIBLE
        }

        return binding.root
    }
}