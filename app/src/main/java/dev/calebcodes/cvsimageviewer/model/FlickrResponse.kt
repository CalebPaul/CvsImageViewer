package dev.calebcodes.cvsimageviewer.model

import com.google.gson.annotations.SerializedName

data class FlickrResponse(
    @SerializedName("link")
    val link: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("modified")
    val modified: String = "",
    @SerializedName("generator")
    val generator: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("items")
    val items: List<FlickrImage>?
)