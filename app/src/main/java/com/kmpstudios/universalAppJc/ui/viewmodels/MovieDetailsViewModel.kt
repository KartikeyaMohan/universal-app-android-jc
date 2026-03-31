package com.kmpstudios.universalAppJc.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmpstudios.universalAppJc.data.models.movies.MovieDetailsResponse
import com.kmpstudios.universalAppJc.domain.useCases.movies.GetMovieDetailsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = MovieDetailsViewModel.HiltFactory::class)
class MovieDetailsViewModel @AssistedInject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    @Assisted private val movieId: Long
): ViewModel() {

    @AssistedFactory
    interface HiltFactory {
        fun create(movieId: Long): MovieDetailsViewModel
    }

    private val _movieDetailResponse = MutableStateFlow<MovieDetailsResponse?>(null)
    val movieDetailResponse = _movieDetailResponse.asStateFlow()

    init {
        getMovieDetails()
    }

    private fun getMovieDetails() {
        viewModelScope.launch {
            val response = getMovieDetailsUseCase.execute(movieId)
            if (response.isSuccess()) {
                _movieDetailResponse.value = response.data
            }
        }
    }
}