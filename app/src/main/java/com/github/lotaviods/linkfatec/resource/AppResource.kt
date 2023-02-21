package com.github.lotaviods.linkfatec.resource

import com.github.lotaviods.linkfatec.model.ErrorState

data class AppResource<T>(
    val data: T?,
    val hasError: Boolean,
    val error: ErrorState?= null
)

