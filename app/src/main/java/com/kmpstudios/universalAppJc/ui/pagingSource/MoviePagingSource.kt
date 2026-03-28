package com.kmpstudios.universalAppJc.ui.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.movies.MovieData
import com.kmpstudios.universalAppJc.data.models.movies.MovieResponse
import com.kmpstudios.universalAppJc.domain.useCases.movies.GetMoviesUseCase

class MoviePagingSource(
    private val getMoviesUseCase: GetMoviesUseCase
): PagingSource<Int, MovieData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieData> {
        val page = params.key ?: 1
        val limit = params.loadSize

        return try {
            val response: GenericResponse<MovieResponse> = getMoviesUseCase.execute(page, limit)

            if (!response.isSuccess()) {
                return LoadResult.Error(
                    Exception(response.errors?.firstOrNull()?.message ?: "Unknown error")
                )
            }

            LoadResult.Page(
                data = response.data?.list ?: emptyList(),
                prevKey = response.data?.meta?.prevPage ?: (if (page == 1) null else page - 1),
                nextKey = response.data?.meta?.nextPage ?: (if (response.data?.list.isNullOrEmpty()) null else page + 1)
            )
        }
        catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieData>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}