package com.kmpstudios.universalAppJc.domain

import com.kmpstudios.universalAppJc.domain.utils.DataTypeHelper.toMultipartBody
import com.kmpstudios.universalAppJc.domain.utils.DataTypeHelper.toRequestBody
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import okio.Buffer
import org.junit.Test
import java.io.File

class DataTypeHelperTest {

    @Test
    fun `String toRequestBody writes plain text`() {
        val body = "hello".toRequestBody()
        val buffer = Buffer()
        body.writeTo(buffer)
        assertEquals("hello", buffer.readUtf8())
    }

    @Test
    fun `null File toMultipartBody returns null`() {
        val file: File? = null
        assertNull(file.toMultipartBody("key"))
    }

    @Test
    fun `File toMultipartBody creates part`() {
        val temp = File.createTempFile("test", ".jpg")
        temp.writeBytes(byteArrayOf(1, 2, 3))
        try {
            val part = temp.toMultipartBody("profile_image")
            assertNotNull(part)
        } finally {
            temp.delete()
        }
    }
}
