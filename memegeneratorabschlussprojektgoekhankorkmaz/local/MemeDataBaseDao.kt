package com.example.memegeneratorabschlussprojektgoekhankorkmaz.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.data.Meme
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.data.MemeData

@Dao
interface MemeDataBaseDao {
    @Insert
    suspend fun insert(meme: Meme)

    @Update
    suspend fun update(meme: Meme)

    @Query("SELECT * FROM Meme")
    fun getAll(): LiveData<List<Meme>>

    @Query("DELETE FROM Meme WHERE id = :key")
    suspend fun deleteByID(key: String)
    @Delete
    suspend fun deleteMeme(meme:Meme)
}