package com.example.fiszkiapp.TitleFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.fiszkiapp.R
import com.example.fiszkiapp.database.FiszkiDatabase
import com.example.fiszkiapp.databinding.FragmentTitleBinding


class TitleFragment : Fragment() {

    private lateinit var viewModel : TitleViewModel
    private lateinit var viewModelFactory: TitleViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val dataSource = FiszkiDatabase.getInstance(application).fiszkiDatabaseDao

        viewModelFactory = TitleViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TitleViewModel::class.java)


       val binding : FragmentTitleBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false)

        val adapter = LangToLangAdapter { item ->
            Navigation
                    .findNavController(view!!)
                    .navigate(TitleFragmentDirections.actionTitleFragmentToTopicsFragment(item))
        }
        binding.list.adapter = adapter


        viewModel.nights.observe(viewLifecycleOwner, Observer {
            adapter.data = it
            adapter.notifyDataSetChanged()
        })

        return binding.root

    }


}