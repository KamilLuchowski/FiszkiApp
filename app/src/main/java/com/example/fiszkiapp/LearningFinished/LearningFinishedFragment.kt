package com.example.fiszkiapp.LearningFinished

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fiszkiapp.R
import com.example.fiszkiapp.database.FiszkiDatabase
import com.example.fiszkiapp.databinding.FragmentLearningFinishedBinding


class LearningFinishedFragment : Fragment() {

    private lateinit var viewModel: LearningFinishedViewModel
    private lateinit var viewModelFactory: LearningFinishedViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val dataSource = FiszkiDatabase.getInstance(application).fiszkiDatabaseDao

        //val topicId = LearningFinishedFragmentArgs.fromBundle(arguments!!).topicId

        viewModelFactory = LearningFinishedViewModelFactory(dataSource, application)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(LearningFinishedViewModel::class.java)

        val binding: FragmentLearningFinishedBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_learning_finished, container, false)


        return binding.root
    }
}