package com.lyft.newsapp.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lyft.newsapp.viewmodel.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController, viewModel: NewsViewModel= hiltViewModel()) {

    var query by remember { mutableStateOf("") }
    val keyBoardController = LocalSoftwareKeyboardController.current

Scaffold(
   topBar = {
       TopAppBar(
           navigationIcon = {
                     IconButton(onClick = {navController.popBackStack() }) {
                       Icon(imageVector = Icons.Default.ArrowBack, tint = MaterialTheme.colorScheme.onSurface,contentDescription = "Back Arrow")
                     }

           },
           title = {
                   TextField(
                       value = query, onValueChange = { query = it },
                       placeholder = { Text(text = "Search News") },
                       singleLine = true,
                       modifier = Modifier.fillMaxWidth(),
                       keyboardOptions = KeyboardOptions.Default.copy(
                           imeAction = ImeAction.Done
                       ),
                       keyboardActions = KeyboardActions(
                           onDone = {
                               if (query.isNotEmpty()) {
                                   viewModel.searchNews(query)
                               }
                               keyBoardController?.hide()
                           }
                       ),
                   )
           },
           actions = {viewModel.searchNews(query)}
       )

   }
) {innerPadding->
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)){
        if (query.isEmpty()){
            EmptySearchScreen()
        }else{
            SearchResultScreen(newsViewModel = viewModel)
        }
    }
}

}

@Composable
fun EmptySearchScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Type something to search for news", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun SearchResultScreen(newsViewModel: NewsViewModel){
    val newsState by newsViewModel.news.observeAsState()
    NewsList(newsState = newsState)
}
