package com.kmpstudios.universalAppJc.ui.utils

import androidx.work.WorkInfo
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object WorkManagerHelper {

    suspend fun WorkManager.isPeriodicWorkScheduled(workName: String): Boolean {
        val workInfos = withContext(Dispatchers.IO) {
            getWorkInfosForUniqueWork(workName).get()
        }

        return workInfos.any {
            it.state == WorkInfo.State.RUNNING ||
            it.state == WorkInfo.State.ENQUEUED
        }
    }
}