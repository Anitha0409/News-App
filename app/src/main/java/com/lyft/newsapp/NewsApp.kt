package com.lyft.newsapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApp:Application() {
    // No explicit initialization needed here
}