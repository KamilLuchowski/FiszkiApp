package com.example.fiszkiapp

class LangToLangDictionary {
    fun learningLanguage(langToLangId: Int): String {
        when(langToLangId){
            0,1,2,3,4,5,6 -> return "en"
            //2,3 -> return "ru"
            else -> return "pl"
        }
    }
    companion object {
        val langName = listOf(Pair(0, R.string.polish), Pair(1, R.string.english),Pair(2, R.string.russian))
    }



}