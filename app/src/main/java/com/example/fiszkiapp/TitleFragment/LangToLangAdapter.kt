package com.example.fiszkiapp.TitleFragment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fiszkiapp.R
import com.example.fiszkiapp.database.LanguageAndLangToLang

class LangToLangAdapter( val listener: (Int) -> Unit): RecyclerView.Adapter<LangToLangAdapter.ViewHolder>() {

    var data = listOf<LanguageAndLangToLang>()

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(data[position])
        data[position].l2l.langToLangId
        holder.itemView.setOnClickListener {  listener(data[position].l2l.langToLangId) }

        if ((position%2) == 1) {
            holder.itemView.setBackgroundColor(Color.parseColor("#eef4be"))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(itemView : View): RecyclerView.ViewHolder(itemView){
        fun bind(item: LanguageAndLangToLang) {
            text1.text = item.language1.name
            text2.text = item.language2.name
        }

        val text1: TextView = itemView.findViewById(R.id.lang1)
        val text2: TextView = itemView.findViewById(R.id.lang2)
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                        .inflate(R.layout.list_item_langtolang, parent, false)
                return ViewHolder(view)
            }
        }
    }
}

