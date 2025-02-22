package com.lyft.newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lyft.newsapp.view.HomeScreen
import com.lyft.newsapp.view.SearchScreen
import com.lyft.newsapp.view.TopStoriesScreen

@Composable
fun NewsNavGraph(){
     val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "HomePage") {
           composable("HomePage"){
               HomeScreen(navController)
           }
        composable("SearchScreen"){
            SearchScreen(navController = navController)
        }
        composable("TopStoriesScreen"){
            TopStoriesScreen(navController = navController)
        }

    }
}