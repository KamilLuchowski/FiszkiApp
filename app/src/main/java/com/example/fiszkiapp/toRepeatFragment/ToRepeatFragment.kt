package com.example.fiszkiapp.toRepeatFragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
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
import com.example.fiszkiapp.LangToLangDictionary
import com.example.fiszkiapp.R
import com.example.fiszkiapp.database.FiszkiDatabase
import com.example.fiszkiapp.databinding.FragmentToRepeatBinding


class ToRepeatFragment: Fragment() {

    private lateinit var viewModel : ToRepeatViewModel
    private lateinit var viewModelFactory: ToRepeatViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val dataSource = FiszkiDatabase.getInstance(application).fiszkiDatabaseDao

        val langToLangId = ToRepeatFragmentArgs.fromBundle(arguments!!).langToLang

        viewModelFactory = ToRepeatViewModelFactory(dataSource, application, langToLangId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ToRepeatViewModel::class.java)

        val binding : FragmentToRepeatBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_to_repeat, container, false)
        binding.ToRepeatFlag.setImageResource(LangToLangDictionary().drawableFlagSourceLanguage(langToLangId, application.applicationContext))

        val adapter = ToRepeatAdapter(application.applicationContext, langToLangId)
        binding.listToRepeatFlashcards.adapter = adapter
        binding.listToRepeatFlashcards.layoutManager = LinearLayoutManager(context)
        setHasOptionsMenu(true)


        viewModel.flashcards.observe(viewLifecycleOwner, Observer {
            adapter.data = it
            adapter.notifyDataSetChanged()
        })



        binding.PlayToRepeatButton.setOnClickListener {
            if (viewModel.flashcards.value?.size == 0)
                Toast.makeText(context, "No flashcards", Toast.LENGTH_SHORT).show()
            else
                Navigation.findNavController(it)
                    .navigate(ToRepeatFragmentDirections
                        .actionToRepeatFragmentToLearningFragment(-1, langToLangId))
        }

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                findNavController().navigateUp()
        }
        return true
    }
}