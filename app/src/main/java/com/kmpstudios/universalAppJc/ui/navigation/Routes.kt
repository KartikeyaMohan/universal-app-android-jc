package com.kmpstudios.universalAppJc.ui.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
sealed class NavKey : Parcelable

@Parcelize
@Serializable
data object Login: NavKey()

@Parcelize
@Serializable
data object Register: NavKey()

@Parcelize
@Serializable
data object Home: NavKey()

@Parcelize
@Serializable
data object Profile: NavKey()

@Parcelize
@Serializable
data object Movie: NavKey()

@Parcelize
@Serializable
data class MovieDetails(val id: Long): NavKey()

@Parcelize
@Serializable
data object Location: NavKey()

@Parcelize
@Serializable
data object LocationTable: NavKey()