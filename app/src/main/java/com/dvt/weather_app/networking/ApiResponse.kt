package com.dvt.weather_app.networking


sealed class ApiResponse<out T> {
    data class Success<out T>(val value: T) : ApiResponse<T>()
    data class Failure<out T>(val apiError: ApiError) : ApiResponse<T>()
}

data class ApiError(val statusCode: Int?, override val message: String, val body: String) :
    Exception(message)