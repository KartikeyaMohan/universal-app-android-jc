package com.kmpstudios.universalAppJc.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.kmpstudios.universalAppJc.ui.navigation.LocalRootNavigator
import com.kmpstudios.universalAppJc.ui.navigation.Login
import com.kmpstudios.universalAppJc.ui.screens.BaseScreen
import com.kmpstudios.universalAppJc.ui.theme.AppTheme
import com.kmpstudios.universalAppJc.ui.utils.TestTags
import com.kmpstudios.universalAppJc.ui.viewmodels.ProfileViewModel

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {

    val colors = AppTheme.colors
    val scrollState = rememberScrollState()
    val rootNavigator = LocalRootNavigator.current
    val profileResponse by profileViewModel.profileResponse.collectAsState()

    BaseScreen {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .semantics { testTag = TestTags.PROFILE_SCREEN }) {
            Spacer(modifier = Modifier.height(50.dp))
            AsyncImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .height(150.dp)
                    .width(150.dp)
                    .align(Alignment.CenterHorizontally),
                model = profileResponse?.profileImageUrl,
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = profileResponse?.name ?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                fontSize = 26.sp,
                color = colors.textPrimary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = profileResponse?.email ?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                fontSize = 20.sp,
                color = colors.textSecondary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(30.dp))
            Button(
                onClick = {
                    profileViewModel.clearTokens()
                    rootNavigator(Login)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = "Logout",
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = colors.textPrimary,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}
