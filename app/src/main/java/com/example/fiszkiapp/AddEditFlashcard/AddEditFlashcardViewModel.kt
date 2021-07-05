package com.example.fiszkiapp.AddEditFlashcard

import android.app.Application
import android.content.ClipDescription
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fiszkiapp.database.FiszkiDatabaseDao
import com.example.fiszkiapp.database.Flashcard
import com.example.fiszkiapp.database.FlashcardAndTopic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONArray
import java.io.IOException


class AddEditFlashcardViewModel(val dataSource: FiszkiDatabaseDao, application: Application, val flashcardId: Int, val topicId: Int, langToLangId: Int): ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(IO + viewModelJob)

    private val _translation: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val translation
        get() = _translation

    private var _flashcardAndTopic: LiveData<FlashcardAndTopic>? = null
    val flashcardAndTopic
        get() = _flashcardAndTopic

    private var _flashcard: LiveData<Flashcard>? = null
    val flashcard
        get() = _flashcard

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        if(flashcardId == -1){
            //new flashcard
        }
        else{
            _flashcardAndTopic = dataSource.getFlashcardandTopic(flashcardId)
            _flashcard = dataSource.getFlashcard(flashcardId)
            uiScope.launch {
            }
        }
    }

    fun deleteFlashcard(flashcardId: Int){
        CoroutineScope(IO).launch {
            dataSource.deleteFlashcard(flashcardId)
        }
    }

    fun addFlashcard(wordText: String, translateText: String, descriptionText: String) {
        CoroutineScope(IO).launch {
            dataSource.insertFlashcard(Flashcard(wordText, translateText, topicId, descriptionText))
        }
    }

    fun updateFlashcard(flashcardId: Int, wordText: String, translateText: String) {
        CoroutineScope(IO).launch {
            dataSource.updateFlashcard(flashcardId, wordText, translateText)
        }
    }

//    fun trans(){ //ten działa
//        val client = OkHttpClient()
//
//        //val postData = JsonObject()
//        val postD = JSONObject()
//        postD.put("name", "morpheus")
//        postD.put("job", "leader")
//
//        //postData.addProperty("name", "morpheus")
//        //postData.addProperty("job", "leader")
//
//        val JSON = MediaType.parse("application/json; charset=utf-8")
//        val postBody: RequestBody = RequestBody.create(JSON, postD.toString())
//        val post: Request = Request.Builder()
//            .url("https://reqres.in/api/users")
//            .post(postBody)
//            .build()
//
//        client.newCall(post).enqueue(object : Callback {
//            override fun onFailure(call: Call?, e: IOException) {
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call?, response: Response) {
//                try {
//                    val responseBody = response.body()
//                    if (!response.isSuccessful) {
//                        throw IOException("Unexpected code $response")
//                    }
//                    Log.i("TRANS", responseBody!!.string())
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//            }
//        })
//    }

    fun translate(lang: String, phase: String) {
        val url =
            "https://microsoft-translator-text.p.rapidapi.com/translate?api-version=3.0&to=$lang&textType=plain&profanityAction=NoAction"
        val client = OkHttpClient()

        val mediaType = MediaType.parse("application/json")
        val body = RequestBody.create(mediaType, "[\r{\r \"Text\": \"$phase\"\r}\r]")
        val request = Request.Builder()
            .url(url)
            .post(body)
            .addHeader("content-type", "application/json")
            .addHeader("x-rapidapi-key", "1ed5baeab2msh921121daa09d3bep1d4224jsnbfcdc0c3ae81")
            .addHeader("x-rapidapi-host", "microsoft-translator-text.p.rapidapi.com")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call?, response: Response) {
                try {
                    val responseBody = response.body()
                    if (!response.isSuccessful) {
                        throw IOException("Unexpected code $response")
                    }
                    responseData(responseBody)
                    //var liveData = LiveData<String>
                    //_translation?.value = jsonobject2.get("text").toString()

//                    val currentName: MutableLiveData<String> by lazy {
//                        MutableLiveData<String>()
//                    }
                    //currentName.value = jsonobject2.get("text").toString()


                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

    private fun responseData(responseBody: ResponseBody?) {
        Log.i("JSONobjectststs", "sdfsdfsd")
        val jsonData: String = responseBody!!.string()
        val jsonarray = JSONArray(jsonData)
        val jsonobject = jsonarray.getJSONObject(0)
        val jsonarray2 = jsonobject.getJSONArray("translations")
        val jsonobject2 = jsonarray2.getJSONObject(0)
        val str = jsonobject2.get("text").toString()
        Log.i("JSONobjecttss", str)
        _translation.postValue(str)


    }
}