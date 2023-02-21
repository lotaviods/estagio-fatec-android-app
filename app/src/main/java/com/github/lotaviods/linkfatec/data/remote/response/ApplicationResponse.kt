package com.github.lotaviods.linkfatec.data.remote.response

class ApplicationResponse<T>(
    val data: T? = null,
    val status: Status? = null,
    val statusCode: Int? = null
) {
    val isSuccessful: Boolean get() = (status == Status.SUCCESS)

    enum class Status {
        SUCCESS,
        BAD_REQUEST,
        UNAUTHORIZED,
        SERVER,
        METHOD_NOT_ALLOWED,
        DESERIALIZED,
        UNDETERMINED,
        FORBIDDEN,
        NOT_FOUND,
        CONNECTION_ERROR
    }
}