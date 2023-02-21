package com.github.lotaviods.linkfatec.model

data class Resource<T>(val data: T, val error: ErrorState?)

sealed interface ErrorState {
    object Authorization : ErrorState
    object Unexpected : ErrorState
    object AccessDenied : ErrorState
    object InternetConnection : ErrorState
    object NotFound : ErrorState
}