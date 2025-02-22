package com.lyft.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyft.newsapp.model.EverythingResponse
import com.lyft.newsapp.repository.NewsRepo
import com.lyft.newsapp.repository.NewsResponseData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepo: NewsRepo):ViewModel() {

    private val _news = MutableLiveData<NewsResponseStatus>()
    val news:LiveData<NewsResponseStatus> = _news

    private val _topHeadLines = MutableLiveData<NewsResponseStatus>()
    val topHeadlines:LiveData<NewsResponseStatus> = _topHeadLines

    private val _categories = MutableLiveData<NewsResponseStatus>()
    val categories:LiveData<NewsResponseStatus> = _categories

    private val _selectedCategory = MutableStateFlow("Latest")
    val selectedCategory = _selectedCategory.asStateFlow()

    init {
        getNewsDetails("world news")
    }
    private fun getNewsDetails(query:String){
        fetchData(
            query = query,
            liveData = _news,
            fetchType = FetchType.News
        )

    }
    fun searchNews(query: String){
        getNewsDetails(query)
    }

    fun getTopHeadlines(country:String){
        fetchData(
            query = country,
            liveData = _topHeadLines,
            fetchType = FetchType.Headlines
        )
    }

    fun getCategoryDetails(category:String){
        fetchData(
            query = category,
            liveData = _categories,
            fetchType = FetchType.Category
        )
    }

    fun onCategorySelected(category:String){
        if(category == _selectedCategory.value) return
        _selectedCategory.value = category
        if(category=="Latest"){
            getTopHeadlines("us")
        }else{
            getCategoryDetails(category)
        }
    }


    private fun fetchData(query:String, liveData:MutableLiveData<NewsResponseStatus>,fetchType:FetchType){
        viewModelScope.launch {
                liveData.postValue(
                    NewsResponseStatus(
                        loading = true,
                        newsData = null,
                        error = null
                    )
                )

                val data = when(fetchType){
                    FetchType.News -> newsRepo.fetchNews(query)
                    FetchType.Headlines -> newsRepo.fetchTopHeadlines(query)
                    FetchType.Category->newsRepo.fetchCategory(query)
                }
                when(data){
                    NewsResponseData.Loading ->{
                        liveData.postValue(
                            NewsResponseStatus(
                                loading = true,
                                newsData = null,
                                error = null

                            )
                        )
                    }
                    is NewsResponseData.Success -> {
                        val newsData = data.newsInfo
                        val filteredData =
                            newsData.articles.filter { !it.description.isNullOrEmpty() }
                        liveData.postValue(
                            NewsResponseStatus(
                                loading = false,
                                newsData = EverythingResponse(articles = filteredData),
                                error = null

                            )
                        )
                    }
                    NewsResponseData.Failure.UnexpectedError -> {
                        liveData.postValue(
                            NewsResponseStatus(
                                loading = false,
                                newsData = null,
                                error = "Some Unexpected error occurred"
                            )
                        )

                    }
                    NewsResponseData.Failure.noInternetConnection ->{
                        liveData.postValue(
                            NewsResponseStatus(
                                loading = false,
                                newsData = null,
                                    error = "Check your internet connection and try again"
                            )
                        )
                    }
                    NewsResponseData.Failure.noNewsFound -> {
                        liveData.postValue(
                            NewsResponseStatus(
                                loading = false,
                                newsData = null,
                                error = "There is no news to show"
                            )
                        )
                    }
                }

        }

    }

    data class NewsResponseStatus(
        val loading:Boolean? = null,
        val newsData:EverythingResponse? = null,
        val error:String? = null
    )
    enum class FetchType {
        News,
        Headlines,
        Category
    }

}