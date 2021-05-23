package com.example.unishare

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostItem(
    @Json(name = "contentURL")
    val contentURL: String,
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "header")
    val header: String,
    @Json(name = "uploadedBy")
    val uploadedBy: String,
    @Json(name = "upvotes")
    val upvotes: Int
)