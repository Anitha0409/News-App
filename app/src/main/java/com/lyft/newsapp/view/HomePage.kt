package com.lyft.newsapp.view
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lyft.newsapp.viewmodel.NewsViewModel


@Composable
fun HomeScreen(navController: NavController, newsViewModel: NewsViewModel = hiltViewModel()) {

    val newsState by newsViewModel.news.observeAsState()

    Column(modifier = Modifier) {

        IconButton(onClick = { navController.navigate("SearchScreen") }) {
            Icon(imageVector = Icons.Filled.Search, contentDescription = "Search Icon")
        }

        Button(onClick = {
         navController.navigate("TopStoriesScreen")
        }
        ) {
            Text(text = "Top Stories")
        }
        newsState?.let { 
            NewsList(newsState = newsState)
        }
        }
    }

