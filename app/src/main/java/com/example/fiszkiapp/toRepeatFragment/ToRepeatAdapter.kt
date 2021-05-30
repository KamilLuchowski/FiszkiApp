package com.example.fiszkiapp.toRepeatFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.fiszkiapp.R
import com.example.fiszkiapp.database.Flashcard

class ToRepeatAdapter(val context: Context, var langToLangId: Int): RecyclerView.Adapter<ToRepeatAdapter.ViewHolder>(){

    var data = listOf<Flashcard>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])

        holder.itemView.setOnClickListener{
            Navigation.findNavController(holder.itemView)
                .navigate(ToRepeatFragmentDirections
                .actionToRepeatFragmentToFlashcardDetailsFragment(data[position].flashcardId, langToLangId))
        }
    }

    class ViewHolder private constructor(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(item: Flashcard) {
            text_word.text = item.word
            text_translation.text = item.translation
        }

        val text_word: TextView = itemView.findViewById(R.id.text_word)
        val text_translation: TextView = itemView.findViewById(R.id.text_translation)


        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.list_item_flashcards, parent, false)
                return ViewHolder(view)
            }
        }
    }
}