package com.dvt.weather_app.respositories

import android.util.Log
import com.dvt.weather_app.networking.ApiError
import com.dvt.weather_app.networking.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseRepository {
    companion object {
        val TAG = BaseRepository::class.simpleName
    }

    suspend fun <T> apiCall(apiCall: suspend () -> T): ApiResponse<T> {
        return withContext(Dispatchers.IO) {
            try {
                ApiResponse.Success(apiCall.invoke())
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG, "exception $e")

                when (e) {
                    is IOException -> ApiResponse.Failure(
                        ApiError(1, "Unable to connect, check your internet connection", "")
                    )

                    is HttpException -> ApiResponse.Failure(extractHttpExceptions(e))

                    is UnknownHostException -> ApiResponse.Failure(
                        ApiError(1, "Unable to connect, check your internet connection", "")
                    )

                    is SocketTimeoutException -> ApiResponse.Failure(
                        ApiError(1, "Unable to connect, check your internet connection", "")
                    )

                    else -> ApiResponse.Failure( ApiError(1, e.message.toString(), ""))
                }
            }
        }
    }

    private fun extractHttpExceptions(e: HttpException): ApiError {
        val body = e.response()?.errorBody()
        val jsonString = body?.string()
        Log.e(TAG,"body ${e.response()?.body().toString()}")
        Log.e(TAG,"json string $jsonString")

        val message = try {
            val jsonObject = JSONObject(jsonString)
            jsonObject.getString("message")
        } catch (exception: JSONException) {
            when (e.code()) {
                500 -> {
                    "Unable to complete request your request, try again later"

                }

                503 -> {
                    "Service temporarily unavailable, try again in a few minutes"
                }

                else -> {
                    "Unable to complete request your request, try again later"

                }
            }
        }

        val errorCode = e.response()?.code()
        return ApiError(errorCode, message, jsonString!!)
    }
}