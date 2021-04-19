package com.example.fiszkiapp

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
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
        Log.i("WTF", 1.toString())

//        supportFragmentManager.addOnBackStackChangedListener {
//            val stackHeight = supportFragmentManager.backStackEntryCount
//            Log.i("WTF", stackHeight.toString())
//            if (stackHeight > 0) { // if we have something on the stack (doesn't include the current shown fragment)
//                supportActionBar?.setDisplayHomeAsUpEnabled(true);
//                supportActionBar?.setHomeButtonEnabled(true)
//            } else {
//                supportActionBar?.setDisplayHomeAsUpEnabled(false)
//                supportActionBar?.setHomeButtonEnabled(false)
//            }
//        }

        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity)
        //val navController = this.findNavController(R.id.myNavHostFragment)
        //NavigationUI.setupActionBarWithNavController(this, navController)

        //val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
       // setSupportActionBar(toolbar)

        // add back arrow to toolbar

        // add back arrow to toolbar
//        if (supportActionBar != null) {
//            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//            supportActionBar!!.setDisplayShowHomeEnabled(true)
//        }
        //supportFragmentManager.addOnBackStackChangedListener(this)

        getActionBar()?.setDisplayHomeAsUpEnabled(true);

        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

        val dataSource = FiszkiDatabase.getInstance(application).fiszkiDatabaseDao

            CoroutineScope(IO).launch {
                if (dataSource.langToLangCount() == 0){
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
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return navController.navigateUp()
    }

}