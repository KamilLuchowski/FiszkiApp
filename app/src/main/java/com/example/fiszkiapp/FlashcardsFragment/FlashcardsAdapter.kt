package com.example.fiszkiapp.FlashcardsFragment

import android.app.Activity
import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.example.fiszkiapp.LangToLangDictionary
import com.example.fiszkiapp.R
import com.example.fiszkiapp.TopicsFragment.TopicsAdapter
import com.example.fiszkiapp.TopicsFragment.TopicsFragmentDirections
import com.example.fiszkiapp.database.Flashcard
import com.example.fiszkiapp.database.LanguageAndLangToLang
import com.example.fiszkiapp.database.Topic
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class FlashcardsAdapter(val context: Context, var langToLangId: Int): RecyclerView.Adapter<FlashcardsAdapter.ViewHolder>(){

    var data = listOf<Flashcard>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], langToLangId)

        holder.itemView.setOnClickListener{
            Navigation.findNavController(holder.itemView)
                .navigate(FlashcardsFragmentDirections
                .actionFlashcardsFragmentToFlashcardDetailsFragment(data[position].flashcardId, langToLangId))
        }
    }

    class ViewHolder private constructor(itemView: View): RecyclerView.ViewHolder(itemView){
        var TTS: TextToSpeech
        init {
            TTS = TextToSpeech(itemView.context,
                TextToSpeech.OnInitListener { status ->
                    if (status != TextToSpeech.ERROR) {
                        Log.d("TTS", "ok")
                    } else {
                        Log.d("TTS", "error")
                    }
                })
        }
        fun bind(item: Flashcard, langToLangId: Int) {
            text_word.text = item.word
            text_translation.text = item.translation
            textToSpeech(item.word, langToLangId)
        }

        val text_word: TextView = itemView.findViewById(R.id.text_word)
        val text_translation: TextView = itemView.findViewById(R.id.text_translation)
        val speakerButton: FloatingActionButton = itemView.findViewById(R.id.flashcards_speaker_button)

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.list_item_flashcards, parent, false)
                return ViewHolder(view)
            }
        }

        private fun textToSpeech(word:String, langToLangId: Int){
            speakerButton.setOnClickListener{
                val dict = LangToLangDictionary()
                TTS.language = Locale(dict.learningLanguage(langToLangId))
                TTS.speak(word, TextToSpeech.QUEUE_FLUSH, null, "")
            }
        }
    }
}