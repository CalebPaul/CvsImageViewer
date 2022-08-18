package dev.calebcodes.cvsimageviewer.viewmodel

import dev.calebcodes.cvsimageviewer.model.FlickrImageItem
import dev.calebcodes.cvsimageviewer.model.Media
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Test

class ImageListViewModelTest {

    private val mockViewModel = mockk<ImageListViewModel>()

    private val testImage = FlickrImageItem("", "", "", Media(""), "", "photo of dog", "", "", "", "0")

    private val list = listOf<FlickrImageItem>(

        testImage,
        testImage.copy(title = "photo of sunset", id = "1"),
        testImage.copy(title = "selfie photo", id = "2")

    )

    @Test
    fun getImageFromIdOrEmptyImage_getsCorrectImage() {
        every {
            mockViewModel.getImageFromIdOrEmptyImage("1", list)
        } answers { callOriginal() }

        val result = mockViewModel.getImageFromIdOrEmptyImage("1", list)

        assertEquals(result, list[1])
        assertEquals(result.title, list[1].title)
    }
}