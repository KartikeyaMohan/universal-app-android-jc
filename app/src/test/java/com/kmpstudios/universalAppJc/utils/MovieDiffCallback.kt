package com.kmpstudios.universalAppJc.utils

import androidx.recyclerview.widget.DiffUtil
import com.kmpstudios.universalAppJc.data.models.movies.MovieData

class MovieDiffCallback: DiffUtil.ItemCallback<MovieData>() {
    override fun areItemsTheSame(
        oldItem: MovieData,
        newItem: MovieData
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: MovieData,
        newItem: MovieData
    ): Boolean = oldItem == newItem
}