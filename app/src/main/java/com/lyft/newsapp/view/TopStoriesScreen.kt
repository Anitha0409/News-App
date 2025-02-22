package com.lyft.newsapp.view
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lyft.newsapp.viewmodel.NewsViewModel


@Composable
fun TopStoriesScreen(navController: NavController, viewModel: NewsViewModel = hiltViewModel()){
    val topHeadlinesState by viewModel.topHeadlines.observeAsState()
    val categoriesState by viewModel.categories.observeAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getTopHeadlines("us")
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            IconButton(
                onClick = { navController.navigate("SearchScreen")},
                modifier = Modifier.align(Alignment.CenterStart)) {
                Icon(imageVector = Icons.Default.Search, contentDescription ="Search Icon" )}
                Text(text = "Headlines", style = MaterialTheme.typography.titleLarge, modifier = Modifier.align(
                    Alignment.Center))

        }
        TopStories(modifier = Modifier,
                   selectedCategory = selectedCategory,
                   onSelectedCategory = {category->
                      viewModel.onCategorySelected(category)
                   })
      if(selectedCategory=="Latest"){
          NewsList(newsState = topHeadlinesState)
      }else{
          NewsList(newsState = categoriesState)
      }
    }
    }



@Composable
fun TopStories(modifier: Modifier, selectedCategory:String, onSelectedCategory:(String) ->Unit){
    val categories = listOf("Latest", "Business", "Entertainment","General","Health","Science","Sports","Technology")
    LazyRow(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
       items(categories){category->
            Text(text = category,
                color = if(category==selectedCategory) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                style = if (category==selectedCategory){
                    MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                } else MaterialTheme.typography.bodyMedium,
                modifier = Modifier.clickable {
                        onSelectedCategory(category)
                })
        }
    }


}