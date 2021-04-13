package com.example.fiszkiapp.TopicsFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fiszkiapp.R
import com.example.fiszkiapp.TitleFragment.LangToLangAdapter
import com.example.fiszkiapp.TitleFragment.TitleViewModel
import com.example.fiszkiapp.TitleFragment.TitleViewModelFactory
import com.example.fiszkiapp.database.FiszkiDatabase
import com.example.fiszkiapp.databinding.FragmentTitleBinding
import com.example.fiszkiapp.databinding.FragmentTopicsBinding

class TopicsFragment : Fragment() {

    private lateinit var viewModel : TopicsViewModel
    private lateinit var viewModelFactory: TopicsViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val dataSource = FiszkiDatabase.getInstance(application).fiszkiDatabaseDao


        viewModelFactory = TopicsViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TopicsViewModel::class.java)

        var langTolangId: Int = TopicsFragmentArgs.fromBundle(arguments!!).langToLang
        Log.i("RRR", langTolangId.toString())
        viewModel.langToLangId = langTolangId
       // val args = TopicsFragmentArgs.fromBundle(arguments!!)

        Toast.makeText(context, "langToLangArg: ${langTolangId}", Toast.LENGTH_LONG).show()


        val binding : FragmentTopicsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_topics, container, false)
        //binding.editTextTextPersonName.setSelectAllOnFocus(true);

        val adapter = TopicsAdapter(application.applicationContext)

        val view = inflater.inflate(R.layout.fragment_topics, container,  false)

        val aaa = view.findViewById<RecyclerView>(R.id.topics_list)
        
        aaa.adapter = adapter
        aaa.layoutManager = LinearLayoutManager(context)

        //binding.topicsList.adapter = adapter
        //binding.topicsList.layoutManager = LinearLayoutManager(context)

        viewModel.langToLangAndTopic.observe(viewLifecycleOwner, Observer {
            adapter.data = it
            Log.i("RRR_2", it.size.toString())
            adapter.notifyDataSetChanged()
        })

        // Inflate the layout for this fragment
        return view
    }
}