package com.example.memegeneratorabschlussprojektgoekhankorkmaz.data

import androidx.room.Entity
import androidx.room.PrimaryKey


data class CaptionImageResponse(
val success: Boolean,
val data: MemeData?,
val error_message: String?
)
@Entity
data class MemeData(
    @PrimaryKey
    val url: String,
    val page_url: String
)

