package com.example.unishare

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class APIClient {
    var retrofit = Retrofit.Builder()
        .baseUrl("https://api.unishare.study/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val apiClient =  retrofit.create(APIRequests::class.java)
}