package com.example.memegeneratorabschlussprojektgoekhankorkmaz.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.data.Meme


@Database(entities = [Meme::class], version = 1)
abstract class MemeDataBase : RoomDatabase() {
    abstract val memeDataBaseDao: MemeDataBaseDao
}

private lateinit var INSTANCE: MemeDataBase
fun getDataBase(context: Context): MemeDataBase {
    synchronized(MemeDataBase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                MemeDataBase::class.java,
                "meme_database"
            )
                .build()
        }
    }
    return INSTANCE
}
