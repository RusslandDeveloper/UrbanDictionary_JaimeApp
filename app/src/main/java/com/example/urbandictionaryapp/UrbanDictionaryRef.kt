package com.example.urbandictionaryapp

import android.app.Application
import android.content.Context

class UrbanDictionaryRef : Application(){

    override fun onCreate() {
        super.onCreate()
        urbanDictionaryContent = applicationContext
    }

companion object{
    const val api_url ="https://mashape-community-urban-dictionary.p.rapidapi.com/"
    const val end_point ="define"
    lateinit var urbanDictionaryContent: Context
}
}