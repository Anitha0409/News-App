package com.lyft.newsapp.repository

import com.lyft.newsapp.model.EverythingResponse

sealed class NewsResponseData {

    data object Loading:NewsResponseData()
    class Success(val newsInfo: EverythingResponse): NewsResponseData()

    sealed class Failure:NewsResponseData(){
        data object noNewsFound: Failure()
        data object  noInternetConnection: Failure()
         data object UnexpectedError: Failure()
    }
}