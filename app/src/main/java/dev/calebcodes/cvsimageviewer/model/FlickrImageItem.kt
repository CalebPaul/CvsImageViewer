package dev.calebcodes.cvsimageviewer.model

data class FlickrImageItem(
    val author: String = "",
    val link: String = "",
    val description: String = "",
    val media: Media = Media(""),
    val published: String = "",
    val title: String = "",
    val authorId: String = "",
    val dateTaken: String = "",
    val tags: String = "",
    val id: String = ""
)