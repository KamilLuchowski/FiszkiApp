package com.example.fiszkiapp.TopicsFragment

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.fiszkiapp.R
import com.example.fiszkiapp.TitleFragment.LangToLangAdapter
import com.example.fiszkiapp.TitleFragment.LangToLangAdapter.ViewHolder.Companion.from
import com.example.fiszkiapp.TitleFragment.TitleFragmentDirections
import com.example.fiszkiapp.database.LangToLangAndTopic
import com.example.fiszkiapp.database.LanguageAndLangToLang
import com.example.fiszkiapp.database.Topic
import kotlin.contracts.contract


class TopicsAdapter(var context: Context): RecyclerView.Adapter<TopicsAdapter.ViewHolder>() {

    var data = listOf<Topic>()
    init {
        Log.i("RRR_4", data.size.toString())

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount() = data.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)

        holder.itemView.setOnClickListener{
            Toast.makeText(context, data[position].topicName, Toast.LENGTH_SHORT).show()
        }
    }

    class ViewHolder private constructor(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(item: Topic, position: Int) {
            text.text = item.topicName
            number.text = (position+1).toString()
        }

        val text: TextView = itemView.findViewById(R.id.topic_text)
        val number: TextView = itemView.findViewById(R.id.rounded_number_text)


        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.list_item_topic, parent, false)
                return ViewHolder(view)
            }
        }



    }
}