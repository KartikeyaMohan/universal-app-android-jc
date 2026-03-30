package com.kmpstudios.universalAppJc.navigation

import androidx.compose.runtime.mutableStateListOf
import com.kmpstudios.universalAppJc.ui.navigation.Home
import com.kmpstudios.universalAppJc.ui.navigation.Profile
import com.kmpstudios.universalAppJc.ui.navigation.Movie
import com.kmpstudios.universalAppJc.ui.navigation.NavKey
import junit.framework.TestCase.assertEquals
import org.junit.Test

class BackStackTest {

    private val backStack =  mutableStateListOf<NavKey>(Home)

    @Test
    fun `initial backstack contains home`() {
        assertEquals(1, backStack.size)
        assertEquals(Home, backStack.first())
    }

    @Test
    fun `navigating to Profile pushes onto backstack`() {
        backStack.add(Profile)

        assertEquals(2, backStack.size)
        assertEquals(Profile, backStack.last())
    }

    @Test
    fun `navigating to Movie pushes onto backstack`() {
        backStack.add(Movie)

        assertEquals(2, backStack.size)
        assertEquals(Movie, backStack.last())
    }

    @Test
    fun `back press pops last entry`() {
        backStack.add(Profile)
        backStack.removeLastOrNull()

        assertEquals(1, backStack.size)
        assertEquals(Home, backStack.last())
    }

    @Test
    fun `back press on root does not crash when stack is size 1`() {
        backStack.removeLastOrNull()

        assertEquals(0, backStack.size)
    }

    @Test
    fun `tab switch clears backstack and sets new root`() {
        backStack.add(Profile)
        backStack.add(Profile)

        backStack.clear()
        backStack.add(Home)

        assertEquals(1, backStack.size)
        assertEquals(Home, backStack.last())
    }

    @Test
    fun `duplicate guard prevents same tab being pushed twice`() {
        val key: NavKey = Home

        if (backStack.last()::class != key::class) {
            backStack.add(key)
        }
        backStack.add(Profile)
        if (backStack.last()::class != Profile::class) {
            backStack.add(Profile)
        }

        assertEquals(2, backStack.size)
    }
}