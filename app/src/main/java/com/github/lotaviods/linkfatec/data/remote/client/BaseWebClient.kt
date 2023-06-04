package com.github.lotaviods.linkfatec.data.remote.client

import com.github.lotaviods.linkfatec.data.remote.response.ApplicationResponse
import com.github.lotaviods.linkfatec.data.remote.response.ApplicationResponse.Status
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseWebClient {
    suspend fun <T> execute(call: suspend () -> retrofit2.Response<T>): ApplicationResponse<T> {
        val response: retrofit2.Response<T>?

        try {
            response = call.invoke()
        } catch (e: Throwable) {
            return when (e.javaClass.name) {
                SocketTimeoutException::class.java.name -> {
                    ApplicationResponse(null, Status.CONNECTION_ERROR)
                }

                UnknownHostException::class.java.name -> {
                    ApplicationResponse(null, Status.CONNECTION_ERROR)
                }
                else -> {
                    ApplicationResponse(null, Status.UNDETERMINED)
                }
            }
        }
        return when (response.code()) {
            in SUCCESS -> {
                ApplicationResponse(response.body(), Status.SUCCESS)
            }

            BAD_REQUEST -> {
                ApplicationResponse(status = Status.BAD_REQUEST)
            }
            UNAUTHORIZED -> {
                ApplicationResponse(status = Status.UNAUTHORIZED)
            }
            else -> {
                ApplicationResponse(null, Status.UNDETERMINED)
            }
        }
    }

    companion object {
        val SUCCESS = 200..300
        const val BAD_REQUEST = 400
        const val UNAUTHORIZED = 401
    }
}
