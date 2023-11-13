package com.example.memegeneratorabschlussprojektgoekhankorkmaz.remote

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.data.CaptionImageResponse
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.data.Meme
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.data.MemeData
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.local.MemeDataBase
import kotlinx.coroutines.delay


class AppRepository(private val api: MemeApi, private val database: MemeDataBase) {


    private val _memeList = MutableLiveData<List<Meme>>()
    val memeList: LiveData<List<Meme>>
        get() = _memeList

    val libraryMemesList : LiveData<List<Meme>> = database.memeDataBaseDao.getAll()


    suspend fun sendPostToApi(
        templateId: String,
        username: String,
        password: String,
        topText: String,
        bottomText: String
    ): CaptionImageResponse {
        return api.retrofitService.postMeme(templateId, username, password, topText, bottomText)
    }



    suspend fun getMemesFromApi() {
        delay(2000)
        val memes = api.retrofitService.getMemes().data.memes
        _memeList.value = memes
    }

}









