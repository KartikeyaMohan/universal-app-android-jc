package com.kmpstudios.universalAppJc.ui.views

import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.kmpstudios.universalAppJc.ui.navigation.Home
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Man
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Man
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.kmpstudios.universalAppJc.ui.navigation.Location
import com.kmpstudios.universalAppJc.ui.navigation.Profile
import com.kmpstudios.universalAppJc.ui.navigation.Movie
import com.kmpstudios.universalAppJc.ui.navigation.NavKey
import com.kmpstudios.universalAppJc.ui.utils.TestTags

data class Tabs(
    val key: NavKey,
    val label: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector = icon
)

val BOTTOM_TABS = listOf(
    Tabs(Home, "Home", Icons.Outlined.Home, Icons.Filled.Home),
    Tabs(Movie, "Movies", Icons.Outlined.Movie, Icons.Filled.Movie),
    Tabs(Location, "Location", Icons.Outlined.LocationOn, Icons.Filled.LocationOn),
    Tabs(Profile, "Profile", Icons.Outlined.Man, Icons.Filled.Man)
)

@Composable
fun BottomBar(
    currentKey: NavKey,
    onTabSelected: (NavKey) -> Unit
) {
    NavigationBar {
        BOTTOM_TABS.forEach { tab ->
            val selected = currentKey::class == tab.key::class

            val tag = when(tab.key) {
                is Home -> TestTags.TAB_HOME
                is Profile -> TestTags.TAB_MORE
                is Movie -> TestTags.TAB_MOVIE
                is Location -> TestTags.TAB_LOCATION
                else -> ""
            }

            NavigationBarItem(
                selected = selected,
                onClick = { onTabSelected(tab.key) },
                modifier = Modifier.testTag(tag),
                icon = {
                    Icon(
                        imageVector = if (selected) tab.selectedIcon else tab.icon,
                        contentDescription = tab.label
                    )
                },
                label = { Text(tab.label) }
            )
        }
    }
}