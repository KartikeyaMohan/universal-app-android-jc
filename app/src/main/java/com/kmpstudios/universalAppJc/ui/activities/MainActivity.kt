package com.kmpstudios.universalAppJc.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.kmpstudios.universalAppJc.ui.theme.UniversalAppTheme
import com.kmpstudios.universalAppJc.ui.navigation.Home
import com.kmpstudios.universalAppJc.ui.navigation.LocalNavigator
import com.kmpstudios.universalAppJc.ui.navigation.LocalOnBack
import com.kmpstudios.universalAppJc.ui.navigation.LocalRootNavigator
import com.kmpstudios.universalAppJc.ui.navigation.Login
import com.kmpstudios.universalAppJc.ui.navigation.NavKey
import com.kmpstudios.universalAppJc.ui.navigation.Register
import com.kmpstudios.universalAppJc.ui.viewmodels.MainViewModel
import com.kmpstudios.universalAppJc.ui.views.BottomBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var entryBuilders: Set<@JvmSuppressWildcards EntryProviderScope<NavKey>.() -> Unit>

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            mainViewModel.isReady.value.not()
        }
        enableEdgeToEdge()
        setContent {
            val isAuthenticated by mainViewModel.isAuthenticated.collectAsState()
            if (mainViewModel.isReady.collectAsState().value && isAuthenticated != null) {
                UniversalAppTheme {
                    App(
                        entryBuilders = entryBuilders,
                        isAuthenticated = isAuthenticated!!
                    )
                }
            }
        }
    }
}

@Composable
fun App(
    entryBuilders: Set<@JvmSuppressWildcards EntryProviderScope<NavKey>.() -> Unit>,
    isAuthenticated: Boolean
) {
    val initialKey: NavKey = if (isAuthenticated) Home else Login
    val backStack = rememberSaveable(
        saver = listSaver(
            save = { it.toList() },
            restore = { mutableStateListOf(*it.toTypedArray()) }
        )
    ) {
        mutableStateListOf(initialKey)
    }
    val isInAuthFlow = backStack.last() is Login || backStack.last() is Register

    CompositionLocalProvider(
        LocalNavigator provides { key -> backStack.add(key) },
        LocalRootNavigator provides { key -> backStack.clear(); backStack.add(key) },
        LocalOnBack provides { backStack.removeLastOrNull() }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                if (!isInAuthFlow) {
                    BottomBar(
                        currentKey = backStack.last(),
                        onTabSelected = { key ->
                            if (backStack.last()::class != key::class) {
                                backStack.add(key)
                            }
                        }
                    )
                }
            }) { innerPadding ->
            NavDisplay(
                backStack = backStack,
                entryDecorators = listOf(
                    rememberSaveableStateHolderNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator()
                ),
                entryProvider = entryProvider {
                    entryBuilders.forEach { builder -> this.builder() }
                },
                onBack = {
                    if (backStack.last() !is Login) {
                        backStack.removeLastOrNull() }
                    },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}