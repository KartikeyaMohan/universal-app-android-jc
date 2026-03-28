package com.kmpstudios.universalAppJc.data.repository

import com.kmpstudios.universalAppJc.data.models.generic.ErrorData
import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.utils.ErrorTypes
import com.kmpstudios.universalAppJc.data.utils.StatusCodes
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseRepository {

    suspend fun <T> callApi(apiCall: suspend () -> Response<GenericResponse<T>>): GenericResponse<T> {
        return try {
            val response = apiCall.invoke()
            handleResponse(response)
        }
        catch (exception: Exception) {
            handleException(exception)
        }
    }

    private fun <T> handleResponse(response: Response<GenericResponse<T>>): GenericResponse<T> {
        return response.body() ?: GenericResponse(status = response.code(), errors = listOf())
    }

    private fun <T> handleException(exception: Exception): GenericResponse<T> {
        val errors = arrayListOf<ErrorData>()
        return if (exception is UnknownHostException ||
            exception.cause?.cause is UnknownHostException ||
            exception is ConnectException ||
            exception.cause?.cause is ConnectException) {
            errors.add(ErrorData(ErrorTypes.INTERNET_ISSUE, "Internet connectivity issue"))
            GenericResponse(errors = errors, status = StatusCodes.REQUEST_TIMEOUT)
        }
        else if (exception is SocketTimeoutException ||
                 exception.cause?.cause is SocketTimeoutException) {
            errors.add(ErrorData(ErrorTypes.SLOW_INTERNET, "Internet connection too slow"))
            GenericResponse(errors = errors, status = StatusCodes.REQUEST_TIMEOUT)
        }
        else if (exception is kotlinx.serialization.SerializationException ||
                 exception.cause?.cause is kotlinx.serialization.SerializationException) {
            errors.add(ErrorData(ErrorTypes.KOTLINX_SERIALIZATION, "Data parsing issue"))
            GenericResponse(errors = errors, status = StatusCodes.UNPROCESSABLE_ENTITY)
        }
        else {
            errors.add(ErrorData(ErrorTypes.DEFAULT_ERROR, "Something went wrong"))
            GenericResponse(errors = errors, status = StatusCodes.INTERNAL_SERVER_ERROR)
        }
    }
}