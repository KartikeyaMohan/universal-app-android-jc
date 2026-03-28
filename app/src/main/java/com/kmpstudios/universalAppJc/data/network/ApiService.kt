package com.kmpstudios.universalAppJc.data.network

import com.kmpstudios.universalAppJc.data.models.auth.LoginRegisterResponse
import com.kmpstudios.universalAppJc.data.models.auth.LoginRequest
import com.kmpstudios.universalAppJc.data.models.auth.TokenData
import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.generic.MessageResponse
import com.kmpstudios.universalAppJc.data.models.locations.LocationRequest
import com.kmpstudios.universalAppJc.data.models.movies.MovieDetailsResponse
import com.kmpstudios.universalAppJc.data.models.movies.MovieResponse
import com.kmpstudios.universalAppJc.data.models.users.ProfileResponse
import com.kmpstudios.universalAppJc.data.network.endpoints.AuthEndpoints
import com.kmpstudios.universalAppJc.data.network.endpoints.LocationEndpoints
import com.kmpstudios.universalAppJc.data.network.endpoints.MovieEndpoints
import com.kmpstudios.universalAppJc.data.network.endpoints.UserEndpoints
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //------------------------------------
    // Authorization
    //------------------------------------

    @Multipart
    @POST(AuthEndpoints.REGISTER)
    suspend fun register(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part profileImage: MultipartBody.Part?
    ): Response<GenericResponse<LoginRegisterResponse>>

    @POST(AuthEndpoints.LOGIN)
    suspend fun login(@Body loginRequest: LoginRequest): Response<GenericResponse<LoginRegisterResponse>>

    @POST(AuthEndpoints.REFRESH)
    suspend fun refresh(): Response<GenericResponse<LoginRegisterResponse>>

    //------------------------------------
    // Users
    //------------------------------------

    @GET(UserEndpoints.PROFILE)
    suspend fun getProfile(): Response<GenericResponse<ProfileResponse>>

    //------------------------------------
    // Movies
    //------------------------------------
    @GET(MovieEndpoints.MOVIE)
    suspend fun getMovies(@Query("page") page: Int, @Query("limit") limit: Int): Response<GenericResponse<MovieResponse>>

    @GET(MovieEndpoints.MOVIE_DETAILS)
    suspend fun getMovieDetails(@Path("id") id: Long): Response<GenericResponse<MovieDetailsResponse>>

    //------------------------------------
    // Location
    //------------------------------------
    @POST(LocationEndpoints.LOCATIONS)
    suspend fun postLocations(@Body locationRequest: LocationRequest): Response<GenericResponse<MessageResponse>>
}