package com.lyft.newsapp.network

import com.lyft.newsapp.model.EverythingResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


object ApiService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create()).build()
    val newsApi = retrofit.create(NewsApi::class.java)
}

interface NewsApi{
@GET("everything")
suspend fun getEverythingDetails(
    @Query("q") q:String? = null,
    @Query("language") language:String? = "en",
    @Query("apiKey")apiKey:String ="25783237c7d34502a1e3d47162f43c9b"
):EverythingResponse

@GET("top-headlines")
suspend fun getTopHeadlines(
    @Query("country")country:String? = null,
    @Query("category")category:String? = null,
    @Query("apiKey")apiKey:String ="25783237c7d34502a1e3d47162f43c9b"):EverythingResponse

}