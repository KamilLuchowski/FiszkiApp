package com.example.fiszkiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.fiszkiapp.database.FiszkiDatabase
import com.example.fiszkiapp.database.LangToLang
import com.example.fiszkiapp.database.Language
import com.example.fiszkiapp.databinding.MainActivityBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity)
        //val navController = this.findNavController(R.id.myNavHostFragment)
        //NavigationUI.setupActionBarWithNavController(this, navController)

        val dataSource = FiszkiDatabase.getInstance(application).fiszkiDatabaseDao

            CoroutineScope(IO).launch {
                if (dataSource.langToLangCount() == 0){
                Log.i("tutaj", "ttuuu")
                dataSource.insertLanguage(Language(0, "polish", "pl"))
                dataSource.insertLanguage(Language(1, "english", "en"))
                dataSource.insertLanguage(Language(2, "russian", "ru"))

                dataSource.insertLangToLang(LangToLang(0, 0, 1))
                dataSource.insertLangToLang(LangToLang(1, 0, 2))
                dataSource.insertLangToLang(LangToLang(2, 1, 0))
                dataSource.insertLangToLang(LangToLang(3, 1, 2))
                dataSource.insertLangToLang(LangToLang(4, 2, 0))
                dataSource.insertLangToLang(LangToLang(5, 2, 1))
            }
        }

    }
//    override fun onSupportNavigateUp(): Boolean {
//        val navController = this.findNavController(R.id.myNavHostFragment)
//        return navController.navigateUp()
//    }
}