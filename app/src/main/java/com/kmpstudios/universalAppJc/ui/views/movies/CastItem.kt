package com.kmpstudios.universalAppJc.ui.views.movies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.kmpstudios.universalAppJc.data.models.movies.Cast

@Composable
fun CastItem(cast: Cast, modifier: Modifier) {
    Box(modifier = modifier.width(120.dp),
        contentAlignment = Alignment.Center) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .height(100.dp)
                    .width(100.dp)
                    .align(Alignment.CenterHorizontally),
                model = cast.imageUrl,
                contentDescription = "Cast Image",
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = cast.name ?: "",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = cast.castType ?: "",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}