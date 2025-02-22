package com.lyft.newsapp.repository
import com.lyft.newsapp.model.EverythingResponse
import com.lyft.newsapp.network.ApiService
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class NewsRepo @Inject constructor() {

     suspend fun fetchNews(query: String):NewsResponseData{
         return fetchData { ApiService.newsApi.getEverythingDetails(query) }
     }
    suspend fun fetchTopHeadlines(country:String):NewsResponseData{
        return fetchData { ApiService.newsApi.getTopHeadlines(country) }
    }
    suspend fun fetchCategory(category:String):NewsResponseData{
        return fetchData { ApiService.newsApi.getTopHeadlines(category = category) }
    }

    }

    private suspend fun fetchData (apiCall: suspend ()->EverythingResponse):NewsResponseData{
        return try {
            val response = apiCall()
            NewsResponseData.Success(response)
        }catch (ex:HttpException){
           if(ex.code()==400){
               NewsResponseData.Failure.noNewsFound
           }else{
               NewsResponseData.Failure.UnexpectedError
           }
        }
        catch (ex:IOException){
            NewsResponseData.Failure.noInternetConnection
        }
    }


