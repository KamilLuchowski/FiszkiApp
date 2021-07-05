package com.example.fiszkiapp.LearningFinished

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import app.futured.donut.DonutSection
import com.example.fiszkiapp.LearningFragment.LearningFragmentDirections
import com.example.fiszkiapp.R
import com.example.fiszkiapp.database.FiszkiDatabase
import com.example.fiszkiapp.databinding.FragmentLearningFinishedBinding
//import com.txusballesteros.widgets.FitChart


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


        val iknewitClicks = LearningFinishedFragmentArgs.fromBundle(arguments!!).iknewitClicks
        val ididntknowClicks = LearningFinishedFragmentArgs.fromBundle(arguments!!).ididntknowClicks

        viewModelFactory = LearningFinishedViewModelFactory(dataSource, application)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(LearningFinishedViewModel::class.java)

        val binding: FragmentLearningFinishedBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_learning_finished, container, false)

        binding.iknewitClicks.text = iknewitClicks.toString()
        binding.ididntknowClicks.text = ididntknowClicks.toString()

        chart(iknewitClicks, ididntknowClicks, binding)

        return binding.root
    }

    private fun chart(
        iknewitClicks: Int,
        ididntknowClicks: Int,
        binding: FragmentLearningFinishedBinding
    ) {
        val sum = iknewitClicks + ididntknowClicks

        val section1 = DonutSection(
            name = "section_1",
            color = Color.parseColor("#E91E63"),
            amount = ididntknowClicks.toFloat()
        )

        val section2 = DonutSection(
            name = "section_2",
            color = Color.parseColor("#8BC34A"),
            amount = iknewitClicks.toFloat()
        )

        binding.donutView.cap = sum.toFloat()
        binding.donutView.submitData(listOf(section1, section2))
    }
}