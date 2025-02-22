package com.lyft.newsapp.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.lyft.newsapp.model.Article
import com.lyft.newsapp.viewmodel.NewsViewModel


@Composable
fun NewsList(newsState:NewsViewModel.NewsResponseStatus?){

    when{
        newsState==null ->{
            LoadingScreen()
        }
        newsState.loading ==true ->{
            LoadingScreen()
        }
        newsState.error !=null ->{
            val errorMessage = newsState.error
            ErrorScreen(errorMessage = errorMessage)
        }
        newsState.newsData !=null -> {
            val newsData = newsState.newsData
            if (newsData.articles.isEmpty()) {
                Text(text = "No articles to display")
            } else {
                LazyColumn {
                    items(newsData.articles) { article ->
                        NewsItem(article = article)
                    }
                }

            }
        }
    }
}
@Composable
fun LoadingScreen(){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ) {
        Text("Loading")
    }
}

@Composable
fun ErrorScreen(errorMessage:String){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = errorMessage)
    }
}

@Composable
fun NewsItem(article: Article) {
    val context = LocalContext.current

    fun openUrl(url:String){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    Column(modifier = Modifier
        .padding(16.dp)
        .clickable { openUrl(article.url) }) {
        val imageUrl = article.urlToImage
        if (!imageUrl.isNullOrEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = article.urlToImage), contentDescription ="Image of the news",
                modifier = Modifier
                    .fillMaxSize()
                    .height(250.dp))
        }else{
            Text("No Image to show")
        }
        article.source.name?.let { Text(text = it, style = TextStyle(color = Color.DarkGray, fontWeight = FontWeight.Bold, fontFamily = FontFamily.SansSerif, fontSize = 15.sp), modifier = Modifier.padding(10.dp)) }
        article.description?.let { Text(text = it, style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp), modifier = Modifier.padding(start = 10.dp)) }

    }
}
