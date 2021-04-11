package com.example.fiszkiapp.TopicsFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.fiszkiapp.R

class TopicsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val args = TopicsFragmentArgs.fromBundle(arguments!!)
        Toast.makeText(context, "langToLangArg: ${args.langToLang}", Toast.LENGTH_LONG).show()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topics, container, false)
    }
}