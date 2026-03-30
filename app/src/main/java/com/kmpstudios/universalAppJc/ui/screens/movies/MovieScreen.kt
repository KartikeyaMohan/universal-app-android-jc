package com.kmpstudios.universalAppJc.ui.screens.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.kmpstudios.universalAppJc.data.models.movies.MovieData
import com.kmpstudios.universalAppJc.ui.navigation.LocalNavigator
import com.kmpstudios.universalAppJc.ui.navigation.MovieDetails
import com.kmpstudios.universalAppJc.ui.navigation.Navigator
import com.kmpstudios.universalAppJc.ui.screens.BaseScreen
import com.kmpstudios.universalAppJc.ui.theme.AppTheme
import com.kmpstudios.universalAppJc.ui.utils.TestTags
import com.kmpstudios.universalAppJc.ui.viewmodels.MovieViewModel
import com.kmpstudios.universalAppJc.ui.views.movies.MovieItem

@Composable
fun MovieScreen(
    movieViewModel: MovieViewModel = hiltViewModel()
) {
    val colors = AppTheme.colors
    val navigator = LocalNavigator.current
    val lazyPagingItems = movieViewModel.moviesPager.collectAsLazyPagingItems()

    BaseScreen(showTopBar = true) {
        when (lazyPagingItems.loadState.refresh) {
            is LoadState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colors.backgroundPrimary)
                        .semantics { testTag = TestTags.LOADING_INDICATOR},
                    contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is LoadState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colors.backgroundPrimary)
                        .semantics { testTag = TestTags.ERROR_MESSAGE },
                    contentAlignment = Alignment.Center) {
                    Text("Something went wrong")
                }
            }
            else -> MovieList(navigator, lazyPagingItems)
        }
    }
}

@Composable
fun MovieList(
    navigator: Navigator,
    lazyPagingItems: LazyPagingItems<MovieData>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .semantics { testTag = TestTags.MOVIE_LIST }
    ) {
        items(
            count = lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey { it.id }
        ) { index ->
            val item = lazyPagingItems[index]
            item?.let {
                MovieItem(
                    data = item,
                    onClick = {
                        navigator(MovieDetails(id = item.id))
                    }
                )
            }
        }

        when (lazyPagingItems.loadState.append) {
            is LoadState.Loading -> item(span = { GridItemSpan(maxLineSpan) }) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .semantics { testTag = TestTags.LOADING_INDICATOR },
                    contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is LoadState.Error -> item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = "Error Loading More",
                    modifier = Modifier.semantics { testTag = TestTags.ERROR_LOAD_MORE }
                )
            }
            else -> Unit
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieScreenPreview() {
    MovieScreen()
}