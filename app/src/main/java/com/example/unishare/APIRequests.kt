package com.example.unishare
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIRequests {
    @GET("content")
    fun getAllPosts() : Call<List<PostItem>>
    @POST("newContent")
    fun pushPost(@Body postItem: PostItem) : Call<List<PostItem>>
}