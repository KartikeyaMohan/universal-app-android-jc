package com.kmpstudios.universalAppJc.models

import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.utils.StatusCodes
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class GenericResponseTest {

    @Test
    fun `isSuccess true for 200 and 201`() {
        assertTrue(GenericResponse<String>(status = StatusCodes.SUCCESS).isSuccess())
        assertTrue(GenericResponse<String>(status = StatusCodes.CREATED).isSuccess())
    }

    @Test
    fun `isSuccess false for other codes`() {
        assertFalse(GenericResponse<String>(status = 400).isSuccess())
    }

    @Test
    fun `isUnauthorized only for 401`() {
        assertTrue(GenericResponse<String>(status = StatusCodes.UNAUTHORIZED).isUnauthorized())
        assertFalse(GenericResponse<String>(status = 200).isUnauthorized())
    }
}
