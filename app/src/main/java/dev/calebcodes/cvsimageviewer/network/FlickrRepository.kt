package dev.calebcodes.cvsimageviewer.network

import dev.calebcodes.cvsimageviewer.model.FlickrResponse
import dev.calebcodes.cvsimageviewer.util.Constant
import dev.calebcodes.cvsimageviewer.util.Resource
import java.lang.Exception
import javax.inject.Inject

class FlickrRepository @Inject constructor(private val flickrService: FlickrService) {

    suspend fun getFlickrImageItems(imageTags: String): Resource<FlickrResponse> {
        return try {
            val result = flickrService.getImages(Constant.FORMAT, Constant.NO_JSON_CALLBACK, imageTags)
            Resource.Success(data = result)
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        }
    }
}