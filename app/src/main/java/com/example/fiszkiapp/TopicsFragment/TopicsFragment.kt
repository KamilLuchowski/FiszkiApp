package com.example.fiszkiapp.TopicsFragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fiszkiapp.R
import com.example.fiszkiapp.database.FiszkiDatabase
import com.example.fiszkiapp.database.Topic


class TopicsFragment : Fragment() {

    private lateinit var viewModel : TopicsViewModel
    private lateinit var viewModelFactory: TopicsViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val dataSource = FiszkiDatabase.getInstance(application).fiszkiDatabaseDao

        val langTolangId: Int = TopicsFragmentArgs.fromBundle(arguments!!).langToLang

        viewModelFactory = TopicsViewModelFactory(dataSource, application, langTolangId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TopicsViewModel::class.java)

        val adapter = TopicsAdapter(application.applicationContext)
        val view = inflater.inflate(R.layout.fragment_topics, container,  false)
        val aaa = view.findViewById<RecyclerView>(R.id.topics_list)

        aaa.adapter = adapter
        aaa.layoutManager = LinearLayoutManager(context)

//        class  simpleItemTouchCallback: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
//
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return true
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                //
//            }
//        }
//
//        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback())
//        itemTouchHelper.attachToRecyclerView(aaa)

        viewModel.langToLangAndTopic.observe(viewLifecycleOwner, Observer {
            adapter.data = it
            adapter.notifyDataSetChanged()
        })

        view.findViewById<Button>(R.id.button).setOnClickListener {
            val editText = view.findViewById<EditText>(R.id.editTextTextPersonName)
            viewModel.insertTopic(Topic( editText.text.toString(), langTolangId))
            activity?.currentFocus?.let { view ->
                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                editText.clearFocus()
                editText.text.clear()
            }
        }

        return view
    }

}