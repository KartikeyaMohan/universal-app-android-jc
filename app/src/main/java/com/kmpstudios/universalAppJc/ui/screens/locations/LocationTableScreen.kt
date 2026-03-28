package com.kmpstudios.universalAppJc.ui.screens.locations

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationSearching
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.kmpstudios.universalAppJc.data.models.locations.LocationEntity
import com.kmpstudios.universalAppJc.ui.viewmodels.LocationViewModel

private data class ColumnDef(val header: String, val width: Dp, val align: TextAlign = TextAlign.Start)

private val COLUMNS = listOf(
    ColumnDef("#",          40.dp,  TextAlign.Center),
    ColumnDef("Date",       100.dp),
    ColumnDef("Time",        80.dp),
    ColumnDef("Latitude",   110.dp, TextAlign.End),
    ColumnDef("Longitude",  110.dp, TextAlign.End),
    ColumnDef("Accuracy",    90.dp, TextAlign.End)
)

@Composable
fun LocationTableScreen(
    locationViewModel: LocationViewModel = hiltViewModel()
) {
    val lazyLocations = locationViewModel.pagedLocations.collectAsLazyPagingItems()
    var showClearDialog by remember { mutableStateOf(false) }

    Scaffold { paddingValues ->
        when {
            // Initial full-screen loading state
            lazyLocations.loadState.refresh is LoadState.Loading -> {
                Box(Modifier.fillMaxSize().padding(paddingValues), Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            // Error state
            lazyLocations.loadState.refresh is LoadState.Error -> {
                val e = (lazyLocations.loadState.refresh as LoadState.Error).error
                Box(Modifier.fillMaxSize().padding(paddingValues), Alignment.Center) {
                    Text("Error: ${e.localizedMessage}", color = MaterialTheme.colorScheme.error)
                }
            }
            // Empty
            lazyLocations.itemCount == 0 -> {
                EmptyState(Modifier.fillMaxSize().padding(paddingValues))
            }
            // Data
            else -> {
                LocationsTable(
                    lazyLocations = lazyLocations,
                    modifier = Modifier.fillMaxSize().padding(paddingValues)
                )
            }
        }
    }
}

@Composable
private fun LocationsTable(
    lazyLocations: LazyPagingItems<LocationEntity>,
    modifier: Modifier = Modifier
) {

    val headerBg  = MaterialTheme.colorScheme.primary
    val evenRowBg = MaterialTheme.colorScheme.surface
    val oddRowBg  = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    val borderCol = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)

    val hScroll = rememberScrollState()

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .horizontalScroll(hScroll)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .background(headerBg)
                        .padding(vertical = 2.dp)
                ) {
                    COLUMNS.forEach { col ->
                        TableCell(
                            text = col.header,
                            width = col.width,
                            align = col.align,
                            textColor = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp)
                        )
                    }
                }
                HorizontalDivider(thickness = 2.dp, color = borderCol)
                LazyColumn {
                    items(
                        count = lazyLocations.itemCount,
                        key = lazyLocations.itemKey { it.id }
                    ) { index ->
                        val loc = lazyLocations[index] ?: return@items
                        val rowBg = if (index % 2 == 0) evenRowBg else oddRowBg
                        Row(
                            modifier = Modifier
                                .background(rowBg)
                                .border(
                                    width = 0.5.dp,
                                    color = borderCol,
                                    shape = RoundedCornerShape(0.dp)
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TableCell(
                                text = "${index + 1}",
                                width = COLUMNS[0].width,
                                align = TextAlign.Center,
                                fontFamily = FontFamily.Monospace,
                                textColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                fontSize = 11.sp
                            )
                            TableCell(
                                text = loc.date,
                                width = COLUMNS[1].width,
                                fontSize = 12.sp
                            )
                            TableCell(
                                text = loc.time,
                                width = COLUMNS[2].width,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 12.sp
                            )
                            TableCell(
                                text = "%.6f".format(loc.latitude),
                                width = COLUMNS[3].width,
                                align = TextAlign.End,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 12.sp,
                                textColor = MaterialTheme.colorScheme.primary
                            )
                            TableCell(
                                text = "%.6f".format(loc.longitude),
                                width = COLUMNS[4].width,
                                align = TextAlign.End,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 12.sp,
                                textColor = MaterialTheme.colorScheme.primary
                            )
                            AccuracyCell(
                                accuracy = loc.accuracy,
                                width = COLUMNS[5].width
                            )
                        }
                        HorizontalDivider(thickness = 0.5.dp, color = borderCol)
                    }
                    item {
                        if (lazyLocations.loadState.append is LoadState.Loading) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(modifier = Modifier.size(24.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.LocationSearching,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No locations yet",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Start the tracking service to begin\ncollecting location data.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun TableCell(
    text: String,
    width: Dp,
    align: TextAlign = TextAlign.Start,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    fontWeight: FontWeight = FontWeight.Normal,
    fontFamily: FontFamily? = null,
    fontSize: androidx.compose.ui.unit.TextUnit = 13.sp,
    modifier: Modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp)
) {
    Box(modifier = Modifier.width(width)) {
        Text(
            text = text,
            modifier = modifier.fillMaxWidth(),
            textAlign = align,
            color = textColor,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            fontSize = fontSize,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun AccuracyCell(accuracy: Float, width: Dp) {
    val (label, chipColor) = when {
        accuracy <= 10f  -> "±${accuracy.toInt()}m" to Color(0xFF34A853)
        accuracy <= 50f  -> "±${accuracy.toInt()}m" to Color(0xFFFBBC04)
        else             -> "±${accuracy.toInt()}m" to Color(0xFFEA4335)
    }
    Box(
        modifier = Modifier.width(width),
        contentAlignment = Alignment.CenterEnd
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 7.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(chipColor.copy(alpha = 0.12f))
                .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
            Text(
                text = label,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Medium,
                color = chipColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationTableScreenPreview() {
    LocationTableScreen()
}