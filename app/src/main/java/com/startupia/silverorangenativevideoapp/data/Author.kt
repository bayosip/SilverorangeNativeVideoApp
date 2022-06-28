package com.startupia.silverorangenativevideoapp.data

import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("id")
    val id:String?,
    @SerializedName("name")
    val name:String?,
)
