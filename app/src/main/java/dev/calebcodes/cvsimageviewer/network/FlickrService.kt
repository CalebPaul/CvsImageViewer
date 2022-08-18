package dev.calebcodes.cvsimageviewer.network

import dev.calebcodes.cvsimageviewer.model.FlickrResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrService {

    @GET("photos_public.gne")
    suspend fun getImages(
        @Query("format") format: String,
        @Query("nojsoncallback") noJsonCallback: String,
        @Query("tags") imageTags: String
    ): FlickrResponse
}