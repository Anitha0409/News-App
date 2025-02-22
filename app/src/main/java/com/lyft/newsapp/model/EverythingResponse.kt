package com.lyft.newsapp.model

data class EverythingResponse(
    val articles: List<Article>
)

data class Article(
    val source:Source,
    val description:String?,
    val urlToImage:String?,
    val url:String
)
data class Source(
    val name:String?
)
