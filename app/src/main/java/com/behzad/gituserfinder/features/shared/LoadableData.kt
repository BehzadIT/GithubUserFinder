package com.behzad.gituserfinder.features.shared

import kotlinx.coroutines.flow.MutableStateFlow


sealed class LoadableData<out T> {
    abstract val data: T?

    data class Loaded<T>(override val data: T) : LoadableData<T>()

    data class Failed<T>(val exception: Exception) : LoadableData<T>() {
        override val data: T? = null
    }

    data class Loading<T>(val additionalInfo: Any? = null) : LoadableData<T>() {
        override val data: T? = null
    }

    data object NotLoaded : LoadableData<Nothing>() {
        override val data: Nothing? = null
    }
}

suspend fun <T> MutableStateFlow<LoadableData<T>>.setAsLoadableData(action: suspend () -> T) {
    value = LoadableData.Loading()
    value = try {
        LoadableData.Loaded(action())
    } catch (e: Exception) {
        LoadableData.Failed(e)
    }
}

