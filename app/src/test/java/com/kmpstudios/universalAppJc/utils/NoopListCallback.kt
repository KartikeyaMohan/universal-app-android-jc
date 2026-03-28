package com.kmpstudios.universalAppJc.utils

import androidx.recyclerview.widget.ListUpdateCallback

class NoopListCallback: ListUpdateCallback {
    override fun onInserted(p0: Int, p1: Int) = Unit
    override fun onRemoved(p0: Int, p1: Int) = Unit
    override fun onMoved(p0: Int, p1: Int) = Unit
    override fun onChanged(p0: Int, p1: Int, p2: Any?) = Unit
}