package com.kmpstudios.universalAppJc.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kmpstudios.universalAppJc.ui.navigation.LocalNavigator
import com.kmpstudios.universalAppJc.ui.navigation.Location
import com.kmpstudios.universalAppJc.ui.navigation.Profile
import com.kmpstudios.universalAppJc.ui.navigation.Movie
import com.kmpstudios.universalAppJc.ui.screens.BaseScreen
import com.kmpstudios.universalAppJc.ui.theme.AppTheme
import com.kmpstudios.universalAppJc.ui.utils.TestTags
import com.kmpstudios.universalAppJc.ui.views.GlassCard

@Composable
fun HomeScreen() {

    val navigator = LocalNavigator.current
    val colors = AppTheme.colors

    BaseScreen {
        Column(
            modifier = Modifier.testTag(TestTags.HOME_SCREEN)
        ) {
            GlassCard(
                modifier = Modifier
                    .padding(20.dp)
                    .clickable(onClick = { navigator(Movie) })
                    .semantics { testTag = TestTags.CARD_HOME_TO_MOVIE },
                color = colors.brandAccent,
            ) {
                Text(
                    text = "Movies",
                    fontSize = 30.sp,
                    color = colors.textPrimary,
                    modifier = Modifier.padding(20.dp).fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            GlassCard(
                modifier = Modifier
                    .padding(20.dp)
                    .clickable(onClick = { navigator(Location) })
                    .semantics { testTag = TestTags.CARD_HOME_TO_LOCATION },
                color = colors.brandAccent,
            ) {
                Text(
                    text = "Location",
                    fontSize = 30.sp,
                    color = colors.textPrimary,
                    modifier = Modifier.padding(20.dp).fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            GlassCard(
                modifier = Modifier
                    .padding(20.dp)
                    .clickable(onClick = { navigator(Profile) })
                    .semantics { testTag = TestTags.CARD_HOME_TO_PROFILE },
                color = colors.brandAccent,
            ) {
                Text(
                    text = "Profile",
                    fontSize = 30.sp,
                    color = colors.textPrimary,
                    modifier = Modifier.padding(20.dp).fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}