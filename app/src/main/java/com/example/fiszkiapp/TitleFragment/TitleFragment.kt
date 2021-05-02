package com.example.fiszkiapp.TitleFragment

import android.R.layout
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ListAdapter
import com.example.fiszkiapp.LangToLangDictionary
import com.example.fiszkiapp.R
import com.example.fiszkiapp.R.layout.*
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

        val binding : FragmentTitleBinding = DataBindingUtil.inflate(inflater, fragment_title, container, false)

        val adapter = LangToLangAdapter(application.applicationContext)
        binding.list.adapter = adapter
        //binding.list.layoutManager = LinearLayoutManager(context)




        val dropdown: Spinner = binding.spinner1

        val list = ArrayList<String>()
        list.add(application.applicationContext.getString(LangToLangDictionary.langName[0].second))
        list.add(application.applicationContext.getString(LangToLangDictionary.langName[1].second))
        list.add(application.applicationContext.getString(LangToLangDictionary.langName[2].second))

        val aa = ArrayAdapter(application.applicationContext,
            layout.simple_spinner_dropdown_item, list)
        dropdown.adapter = aa

        dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                srcLangChange(dropdown, adapter)
            }
        }

        viewModel._languageAndLangToLang.observe(viewLifecycleOwner, Observer {
            srcLangChange(dropdown, adapter)
        })

        return binding.root
    }

    private fun srcLangChange(
        dropdown: Spinner,
        adapter: LangToLangAdapter
    ) {
        when {
            dropdown.selectedItemPosition == 0 -> adapter.data =
                viewModel._languageAndLangToLang.value!!.subList(0, 2)
            dropdown.selectedItemPosition == 1 -> adapter.data =
                viewModel._languageAndLangToLang.value!!.subList(2, 4)
            dropdown.selectedItemPosition == 2 -> adapter.data =
                viewModel._languageAndLangToLang.value!!.subList(4, 6)
        }

        adapter.notifyDataSetChanged()
    }
}