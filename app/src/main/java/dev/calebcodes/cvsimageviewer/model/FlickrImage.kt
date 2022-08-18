package dev.calebcodes.cvsimageviewer.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class FlickrImage(
    @SerializedName("author") private val author: String = "",
    @SerializedName("link") private val link: String = "",
    @SerializedName("description") private val description: String = "",
    @SerializedName("media") private val media: Media,
    @SerializedName("published") private val published: String = "",
    @SerializedName("title") private val title: String = "",
    @SerializedName("author_id") private val authorId: String = "",
    @SerializedName("date_taken") private val dateTaken: String = "",
    @SerializedName("tags") private val tags: String = ""
) {
    companion object {
        fun toImageItem(flickrImage: FlickrImage): FlickrImageItem {
            return FlickrImageItem(
                author = flickrImage.author,
                link = flickrImage.link,
                description = flickrImage.description,
                media = flickrImage.media,
                published = flickrImage.published,
                title = flickrImage.title,
                authorId = flickrImage.authorId,
                dateTaken = flickrImage.dateTaken,
                tags = flickrImage.tags,
                id = UUID.randomUUID().toString()
            )
        }
    }
}
