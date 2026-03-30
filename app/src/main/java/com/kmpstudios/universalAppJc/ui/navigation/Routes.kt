package com.kmpstudios.universalAppJc.ui.navigation

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
sealed class NavKey : Parcelable {
    @IgnoredOnParcel
    open val showTopBar: Boolean = false
    @IgnoredOnParcel
    open val showBottomBar: Boolean = false
}

@Parcelize
@Serializable
data object Login: NavKey()

@Parcelize
@Serializable
data object Register: NavKey()

@Parcelize
@Serializable
data object Home: NavKey() {
    @IgnoredOnParcel
    override val showTopBar = true
    @IgnoredOnParcel
    override val showBottomBar = true
}

@Parcelize
@Serializable
data object Profile: NavKey() {
    @IgnoredOnParcel
    override val showBottomBar = true
}

@Parcelize
@Serializable
data object Movie: NavKey() {
    @IgnoredOnParcel
    override val showTopBar = true
    @IgnoredOnParcel
    override val showBottomBar = true
}

@Parcelize
@Serializable
data class MovieDetails(val id: Long): NavKey()

@Parcelize
@Serializable
data object Location: NavKey() {
    @IgnoredOnParcel
    override val showTopBar = true
    @IgnoredOnParcel
    override val showBottomBar = true
}

@Parcelize
@Serializable
data object LocationTable: NavKey()