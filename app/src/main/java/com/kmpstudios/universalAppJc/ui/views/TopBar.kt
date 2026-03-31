package com.kmpstudios.universalAppJc.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kmpstudios.universalAppJc.R
import com.kmpstudios.universalAppJc.ui.theme.AppTheme

@Composable
fun TopBar() {
    val colors = AppTheme.colors
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        shape = RoundedCornerShape(0.dp),
        colors = CardColors(
            containerColor = colors.backgroundElevated,
            disabledContentColor = colors.backgroundElevated,
            contentColor = colors.backgroundElevated,
            disabledContainerColor = colors.backgroundElevated,
        )
    ) {
        Box(modifier = Modifier
            .fillMaxSize(),
            contentAlignment = Alignment.Center) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.padding(start = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(50.dp),
                        painter = painterResource(R.mipmap.app_launcher_icon),
                        contentDescription = "App Icon",
                        contentScale = ContentScale.Crop,
                    )
                    Text(
                        text = "Universal App JC",
                        modifier = Modifier.padding(start = 15.dp),
                        fontSize = 20.sp,
                        color = colors.textPrimary
                    )
                }
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notification Icon",
                    modifier = Modifier.padding(end = 20.dp).size(30.dp),
                    tint = colors.textPrimary
                )
            }
        }
    }
}