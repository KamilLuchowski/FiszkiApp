package com.example.fiszkiapp

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
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
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val folder = File(
            Environment.getExternalStorageDirectory().toString() +
                    File.separator.toString() + "FiszkiApp"  +
                    File.separator.toString() + "database")

        if (!folder.exists()) {
            folder.mkdirs()
        }
        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity)

        actionBar?.setDisplayHomeAsUpEnabled(true);

        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
        val requestCode = 0
        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(WRITE_EXTERNAL_STORAGE), requestCode)

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