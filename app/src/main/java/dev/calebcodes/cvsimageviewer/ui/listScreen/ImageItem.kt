package dev.calebcodes.cvsimageviewer.ui.listScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import dev.calebcodes.cvsimageviewer.R
import dev.calebcodes.cvsimageviewer.model.FlickrImageItem

@Composable
fun ImageItem(image: FlickrImageItem, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(220.dp)
            .clickable { onClick(image.id) },
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                GlideImage(
                    imageModel = image.media.m,
                    contentScale = ContentScale.Fit,
                    contentDescription = stringResource(R.string.image_item_CD),
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .height(160.dp)
                )
            }
            Text(
                text = image.title,
                style = MaterialTheme.typography.body1,
                maxLines = 2,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}