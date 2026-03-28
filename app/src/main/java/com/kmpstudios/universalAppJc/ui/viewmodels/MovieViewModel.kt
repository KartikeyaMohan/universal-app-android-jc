package com.kmpstudios.universalAppJc.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kmpstudios.universalAppJc.data.models.movies.MovieData
import com.kmpstudios.universalAppJc.domain.useCases.movies.GetMoviesUseCase
import com.kmpstudios.universalAppJc.ui.pagingSource.MoviePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
): ViewModel() {

    val moviesPager: Flow<PagingData<MovieData>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            prefetchDistance = 2,
            enablePlaceholders = false,
            initialLoadSize = 10
        ),
        pagingSourceFactory = { MoviePagingSource(getMoviesUseCase) }
    ).flow.cachedIn(viewModelScope)
}