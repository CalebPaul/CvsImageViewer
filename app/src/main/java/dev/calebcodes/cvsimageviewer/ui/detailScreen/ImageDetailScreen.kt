package dev.calebcodes.cvsimageviewer.ui.detailScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.core.text.parseAsHtml
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.glide.GlideImage
import dev.calebcodes.cvsimageviewer.viewmodel.ImageListViewModel

@Composable
fun ImageDetailScreen(imageListViewModel: ImageListViewModel = hiltViewModel(), imageId: String?) {
    val id = imageId ?: "-1"
    val image = imageListViewModel.imageList.value.data.let {
        return@let imageListViewModel.getImageFromIdOrEmptyImage(id, it)
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                imageModel = image.media.m,
                contentScale = ContentScale.FillWidth,
                contentDescription = "Image",
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .weight(.6f)
            )
            Column(modifier = Modifier.weight(.4f)) {
                Text(
                    text = image.title,
                    style = MaterialTheme.typography.h6,
                    maxLines = 2,
                    modifier = Modifier
                        .padding(4.dp)
                )
                Divider()
                Text(
                    text = "Author: ${image.author}",
                    style = MaterialTheme.typography.body1,
                    maxLines = 2,
                    modifier = Modifier.padding(4.dp)
                )
                Divider()
                Text(
                    text = "Description",
                    color = MaterialTheme.colors.secondary,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                Text(
                    text = image.description.parseAsHtml(
                        flags = HtmlCompat.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE
                    ).toString(),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .verticalScroll(
                            state = rememberScrollState()
                        )
                        .padding(horizontal = 4.dp)
                )
            }
        }
    }
}
