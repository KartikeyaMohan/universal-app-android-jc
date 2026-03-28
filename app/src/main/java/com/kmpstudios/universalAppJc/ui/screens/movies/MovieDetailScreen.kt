package com.kmpstudios.universalAppJc.ui.screens.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kmpstudios.universalAppJc.ui.utils.TestTags
import com.kmpstudios.universalAppJc.ui.viewmodels.MovieDetailsViewModel
import com.kmpstudios.universalAppJc.ui.views.VideoPlayer
import com.kmpstudios.universalAppJc.ui.views.movies.CastItem

@Composable
fun MovieDetailScreen(
    movieDetailsViewModel: MovieDetailsViewModel
) {
    val movieDetailResponse by movieDetailsViewModel.movieDetailResponse.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    var isFullScreen by rememberSaveable { mutableStateOf(false) }

    if (movieDetailResponse != null) {
        Box(modifier = Modifier.fillMaxSize().padding(20.dp)) {
            val playerModifier = if (isFullScreen) {
                Modifier.fillMaxSize()
            } else {
                Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .align(Alignment.TopCenter)
            }
            val trailerUrl = movieDetailResponse?.trailerUrl
            if (!trailerUrl.isNullOrEmpty()) {
                VideoPlayer(
                    videoUrl = trailerUrl,
                    isFullScreen = isFullScreen,
                    onFullScreenToggle = { isFullScreen = !isFullScreen },
                    modifier = playerModifier
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Box(modifier = Modifier.height(210.dp))
                Spacer(modifier = Modifier.padding(top = 10.dp))
                Text(
                    text = movieDetailResponse?.name ?: "",
                    fontSize = 30.sp,
                    modifier = Modifier.semantics { testTag = TestTags.MOVIE_NAME }
                )
                Spacer(modifier = Modifier.padding(top = 10.dp))
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
                        text = if (movieDetailResponse?.rating != null) movieDetailResponse?.rating.toString() else "",
                        fontSize = 16.sp,
                        modifier = Modifier.semantics { testTag = TestTags.MOVIE_RATING }
                    )
                }
                Spacer(modifier = Modifier.padding(top = 10.dp))
                Text(
                    text = movieDetailResponse?.description ?: "",
                    fontSize = 14.sp,
                    modifier = Modifier.testTag(TestTags.MOVIE_DESCRIPTION)
                )
                Spacer(modifier = Modifier.padding(top = 10.dp))
                Text(
                    text = "Cast",
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.padding(top = 10.dp))
                movieDetailResponse?.casts?.let { list ->
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.testTag(TestTags.CAST_LIST)
                    ) {
                        items(
                            items = list,
                            key = { it.id }
                        ) { cast ->
                            CastItem(
                                cast,
                                modifier = Modifier.semantics { testTag = TestTags.CAST_ITEM }
                            )
                        }
                    }
                }
            }
        }
    }
    else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .semantics { testTag = TestTags.LOADING_INDICATOR },
            contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailScreenPreview() {
    MovieDetailScreen(hiltViewModel())
}