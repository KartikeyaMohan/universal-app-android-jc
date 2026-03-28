package com.kmpstudios.universalAppJc.navigation

import com.kmpstudios.universalAppJc.ui.navigation.Home
import com.kmpstudios.universalAppJc.ui.navigation.More
import com.kmpstudios.universalAppJc.ui.navigation.Movie
import com.kmpstudios.universalAppJc.ui.navigation.NavKey
import com.kmpstudios.universalAppJc.ui.navigation.Navigator
import org.junit.Assert.assertEquals
import org.junit.Test

class NavigatorTest {

    @Test
    fun `navigator callback receives correct NavKey`() {
        val navigateTo = mutableListOf<NavKey>()
        val navigator: Navigator = { key -> navigateTo.add(key) }

        navigator(Home)
        navigator(Movie)
        navigator(More)

        assertEquals(listOf(Home, Movie, More), navigateTo)
    }
}