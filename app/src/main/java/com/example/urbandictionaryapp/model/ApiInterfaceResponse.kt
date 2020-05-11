package com.example.urbandictionaryapp.model

import android.content.Context
import android.net.ConnectivityManager
import com.example.urbandictionaryapp.BuildConfig
import com.example.urbandictionaryapp.UrbanDictionaryRef
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterfaceResponse {
    @GET(UrbanDictionaryRef.end_point)
    @Headers(
        value =[
            "x-rapidapi-host:mashape-community-urban-dictionary.p.rapidapi.com",
            "x-rapidapi-key:444308ff34msh24bd974a9a24549p1c11d8jsn59dd26983d17"
        ]

    )

    fun getDefinition(@Query("term") input: String): Call<ItemDescriptionResponse>

    companion object {
        fun initRetrofit(): ApiInterfaceResponse {
            return Retrofit.Builder()
                .client(client)
                .baseUrl(UrbanDictionaryRef.api_url)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(ApiInterfaceResponse::class.java)
        }
        val client: OkHttpClient by lazy {
            initClient()

        }

        private fun initClient(): OkHttpClient {
            val client = OkHttpClient.Builder()
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            if (BuildConfig.DEBUG) {
                client.addInterceptor(logger)
            }
            client.addInterceptor() {
                var request = it.request()
                request = if (offLineMode())
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, max-age=" + 2 * 60
                    ).build()
                else
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 3 * 60
                    ).build()
                it.proceed(request)
            }
                .cache(cacheConfig())
                .build()
            return client.build()
        }

        private fun cacheConfig(): Cache {
            val cacheSize = (5 * 1024 * 1024).toLong()
            return Cache(UrbanDictionaryRef.urbanDictionaryContent.cacheDir, cacheSize)
        }

        private fun offLineMode(): Boolean {
            val connectiviyManager: ConnectivityManager = UrbanDictionaryRef.urbanDictionaryContent
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            var isConnected = false
            connectiviyManager.activeNetworkInfo?.let {
                isConnected = it.isConnected
            }

            return isConnected
        }

    }


}