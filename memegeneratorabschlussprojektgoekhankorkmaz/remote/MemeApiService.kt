package com.example.memegeneratorabschlussprojektgoekhankorkmaz.remote

import com.example.memegeneratorabschlussprojektgoekhankorkmaz.data.CaptionImageResponse
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.data.Response
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

const val BASE_URL = "https://api.imgflip.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MemeApiService {
    @GET("get_memes")
    suspend fun getMemes(): Response

    @FormUrlEncoded
    @POST("caption_image")
    suspend fun postMeme(
        @Field("template_id") templateId: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("text0") topText: String,
        @Field("text1") bottomText: String
    ): CaptionImageResponse

}

object MemeApi{
    val retrofitService: MemeApiService by lazy { retrofit.create(MemeApiService::class.java) }
}


