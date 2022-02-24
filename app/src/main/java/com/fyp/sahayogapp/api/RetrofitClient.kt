package com.fyp.sahayogapp.api

import com.fyp.sahayogapp.api.API_URL.BASE_URL
import com.fyp.sahayogapp.utils.PreferenceHelper.getAccessToken
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
            client
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("token", getAccessToken()!!)
                    chain.proceed(request.build())
                }
                .addInterceptor(logging)

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

}
