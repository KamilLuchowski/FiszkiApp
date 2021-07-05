package com.example.fiszkiapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.provider.Settings.Global.getString

class LangToLangDictionary {
    fun learningLanguage(langToLangId: Int): String {
        when(langToLangId){
            0,5 -> return "en"
            1,3 -> return "ru"
            else -> return "pl"
        }
    }

    fun sourceLanguage(langToLangId: Int): String {
        when(langToLangId){
            0,1 -> return "pl"
            2,3 -> return "en"
            else -> return "ru"
        }
    }

    fun sourceLanguageLong(langToLangId: Int, context: Context): String {
        when(langToLangId){
            0,1 -> return context.getString(R.string.polish)
            2,3 -> return context.getString(R.string.english)
            else -> return context.getString(R.string.russian)
        }
    }

    fun learningLanguageLong(langToLangId: Int, context: Context): String {
        when(langToLangId){
            0,5 -> return context.getString(R.string.english)
            1,3 -> return context.getString(R.string.russian)
            else -> return context.getString(R.string.polish)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun drawableFlagLearningLanguage(langToLangId: Int, context: Context): Int {
        when (langToLangId) {
            0, 5 -> return R.drawable.uk_flag_button_round
            1, 3 -> return R.drawable.russia_flag_button_round
            else -> return R.drawable.poland_flag_button_round
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun drawableFlagSourceLanguage(langToLangId: Int, context: Context): Int {
        when (langToLangId) {
            0, 1 -> return R.drawable.poland_flag_button_round
            2, 3 -> return R.drawable.uk_flag_button_round
            else -> return R.drawable.russia_flag_button_round
        }
    }

    companion object {
        val langName = listOf(Pair(0, R.string.polish), Pair(1, R.string.english),Pair(2, R.string.russian))
    }





}