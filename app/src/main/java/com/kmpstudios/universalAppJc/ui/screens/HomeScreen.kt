package com.kmpstudios.universalAppJc.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.kmpstudios.universalAppJc.ui.utils.TestTags
import com.kmpstudios.universalAppJc.ui.views.movies.beigeColor
import com.kmpstudios.universalAppJc.ui.views.movies.champagneColor

@Composable
fun HomeScreen() {

    val navigator = LocalNavigator.current

    Column(
        modifier = Modifier.testTag(TestTags.HOME_SCREEN)
    ) {
        Card(
            modifier = Modifier
                .padding(20.dp)
                .semantics { testTag = TestTags.CARD_HOME_TO_MOVIE },
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = beigeColor
            ),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(
                width = 0.5.dp,
                color = champagneColor
            ),
            onClick = { navigator(Movie) }
        ) {
            Text(
                text = "Movies",
                fontSize = 30.sp,
                color = Color.Black,
                modifier = Modifier.padding(20.dp).fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        Card(
            modifier = Modifier
                .padding(20.dp)
                .semantics { testTag = TestTags.CARD_HOME_TO_LOCATION },
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = beigeColor
            ),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(
                width = 0.5.dp,
                color = champagneColor
            ),
            onClick = { navigator(Location) }
        ) {
            Text(
                text = "Location",
                fontSize = 30.sp,
                color = Color.Black,
                modifier = Modifier.padding(20.dp).fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        Card(
            modifier = Modifier
                .padding(20.dp)
                .semantics { testTag = TestTags.CARD_HOME_TO_MORE },
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = beigeColor
            ),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(
                width = 0.5.dp,
                color = champagneColor
            ),
            onClick = { navigator(Profile) }
        ) {
            Text(
                text = "Profile",
                fontSize = 30.sp,
                color = Color.Black,
                modifier = Modifier.padding(20.dp).fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}