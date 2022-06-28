package com.startupia.silverorangenativevideoapp.data

import com.google.gson.annotations.SerializedName

data class SOvideo(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("hrlsURL")
    val hrls: String,
    @SerializedName("fullURL")
    val fullURL: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("author")
    val author: Author,
) {
}