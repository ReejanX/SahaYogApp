package com.fyp.sahayogapp.api

import com.fyp.sahayogapp.api.API_URL.BASE_URL
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory




class RetrofitClient {

    companion object {

        fun getRetrofitInstance () : Retrofit {
            val logging = HttpLoggingInterceptor()
            logging.level = (HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
            client.addInterceptor(logging)
//            client.addInterceptor(Interceptor { chain ->
//                val original = chain.request()
//                val requestt = original.newBuilder().header("Au").build()
//                chain.proceed(requestt)
//            }
//
//            )


           return Retrofit.Builder()
                .baseUrl(BASE_URL)
               .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

}
