package com.example.memegeneratorabschlussprojektgoekhankorkmaz.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Meme(
    @PrimaryKey
    val id: String,
    val url: String,
    val box_count:Int = 2
)

