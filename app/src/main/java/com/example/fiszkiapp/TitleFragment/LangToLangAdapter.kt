package com.example.fiszkiapp.TitleFragment

import android.content.Context
import android.graphics.drawable.Drawable
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.fiszkiapp.LangToLangDictionary
import com.example.fiszkiapp.R
import com.example.fiszkiapp.database.LanguageAndLangToLang


class LangToLangAdapter(val context: Context): RecyclerView.Adapter<LangToLangAdapter.ViewHolder>() {

    var data = listOf<LanguageAndLangToLang>()

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(data[position], context)
        holder.itemView.setOnClickListener{
            Navigation
                .findNavController(holder.itemView)
                .navigate(TitleFragmentDirections.actionTitleFragmentToTopicsFragment(data[position].l2l.langToLangId))
        }
    }

    class ViewHolder private constructor(itemView : View): RecyclerView.ViewHolder(itemView){

        fun bind(item: LanguageAndLangToLang, context: Context) {

            text1.text  = context.getString(LangToLangDictionary.langName[item.language2.languageId].second)
            l2lFlag.setImageResource(LangToLangDictionary().drawableFlagLearningLanguage(item.l2l.langToLangId, context))
        }

        val text1: TextView = itemView.findViewById(R.id.lang1)
        val l2lFlag: ImageView = itemView.findViewById(R.id.l2lFlag)
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

