package com.example.fiszkiapp.TitleFragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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

         val ndb = FiszkiDatabase.getInstance(context!!);
        ndb.getOpenHelper();

        viewModelFactory = TitleViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TitleViewModel::class.java)

        val binding : FragmentTitleBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false)

        val adapter = LangToLangAdapter(application.applicationContext)
        binding.list.adapter = adapter
        //binding.list.layoutManager = LinearLayoutManager(context)

        viewModel._nights.observe(viewLifecycleOwner, Observer {
            adapter.data = it
            Log.i("wtf", "xd")
            adapter.notifyDataSetChanged()
        })


        return binding.root
    }
}