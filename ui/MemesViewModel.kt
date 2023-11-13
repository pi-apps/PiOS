package com.example.memegeneratorabschlussprojektgoekhankorkmaz.ui

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.data.CaptionImageResponse
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.data.Meme
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.data.MemeData
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.local.getDataBase
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.remote.AppRepository
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.remote.MemeApi
import kotlinx.coroutines.launch

class MemesViewModel(application: Application) : AndroidViewModel(application) {

    val TAG = "MainViewModel"
    private val dataBase = getDataBase(application)
    private val repository = AppRepository(MemeApi, dataBase)
    val imageList = repository.memeList
    val libraryList = repository.libraryMemesList
    private val _currentMeme = MutableLiveData<Meme>()
    val currentMeme: LiveData<Meme>
        get() = _currentMeme
    init {
        loadMemes()
    }
    fun addChoosenMeme(pickedMeme: Meme) {
        _currentMeme.value = pickedMeme
    }
    fun getChoosenMeme(): Meme? {
        return _currentMeme.value
    }
    suspend fun sendPostToRepo(
        templateId: String,
        username: String,
        password: String,
        topText: String,
        bottomText: String
    ): CaptionImageResponse {
        return repository.sendPostToApi(templateId, username, password, topText, bottomText)
    }

    fun insertMemeToLibrary(editedMeme: Meme) {

        viewModelScope.launch {
            try{
                dataBase.memeDataBaseDao.insert(editedMeme)
            }catch(e:Exception){
                Log.e(TAG,"ERROR INSERTING MEME : $e")
            }
        }
    }
    fun loadMemes() {
        viewModelScope.launch {
            try {
                repository.getMemesFromApi()
            } catch (e: Exception) {
                Log.e(TAG, "ERROR LOADIN DATA : $e")
            }
        }

    }
    fun deleteMeme(meme: Meme) {
        viewModelScope.launch {
            try{
            dataBase.memeDataBaseDao.deleteMeme(meme)
            } catch (e:Exception) {
                Log.e(TAG,"Error DELETE MEME: $e")
            }
        }
    }

}






