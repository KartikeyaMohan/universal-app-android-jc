package com.kmpstudios.universalAppJc.ui.views.movies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.kmpstudios.universalAppJc.data.models.movies.MovieData
import com.kmpstudios.universalAppJc.ui.theme.AppTheme
import com.kmpstudios.universalAppJc.ui.utils.TestTags
import com.kmpstudios.universalAppJc.ui.views.GlassCard

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    data: MovieData,
    onClick: (Long) -> Unit = {}) {

    val colors = AppTheme.colors

    GlassCard(
        modifier = modifier
            .padding(top = 15.dp, start = 10.dp, end = 10.dp)
            .clickable(onClick = { onClick(data.id) })
            .semantics { testTag = TestTags.MOVIE_ITEM },
        color = colors.brandAccent
    ) {
        Box {
            Column {
                AsyncImage(
                    modifier = Modifier.fillMaxWidth().aspectRatio(2f / 3f),
                    model = data.heroImageUrl,
                    contentDescription = "Hero Image",
                    contentScale = ContentScale.Crop
                )
                Column(modifier = Modifier.padding(start = 10.dp, top = 15.dp, end = 10.dp, bottom = 10.dp)) {
                    Text(
                        text = data.name ?: "",
                        color = colors.textPrimary,
                        fontSize = 18.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.semantics { testTag = TestTags.MOVIE_NAME }
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Rating",
                            modifier = Modifier.size(25.dp).padding(end = 5.dp),
                            tint = Color.Yellow
                        )
                        Text(
                            text = data.rating.toString(),
                            color = colors.textSecondary,
                            fontSize = 12.sp,
                            modifier = Modifier.semantics { testTag = TestTags.MOVIE_RATING }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {
    MovieItem(
        data = MovieData(1, "Tenet", "", 4.0F)
    )
}