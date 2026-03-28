package com.kmpstudios.universalAppJc.utils

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onAllNodesWithTag

fun ComposeContentTestRule.waitUntilDisplayed(
    tag: String,
    timeoutMillis: Long = 3_000
) {
    waitUntil(timeoutMillis = timeoutMillis) {
        onAllNodesWithTag(tag)
            .fetchSemanticsNodes().isNotEmpty()
    }
}

fun ComposeContentTestRule.waitUntilGone(
    tag: String,
    timeoutMillis: Long = 3_000
) {
    waitUntil(timeoutMillis = timeoutMillis) {
        onAllNodesWithTag(tag)
            .fetchSemanticsNodes().isEmpty()
    }
}