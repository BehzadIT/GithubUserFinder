package com.behzad.gituserfinder.features.shared

import kotlinx.coroutines.flow.MutableStateFlow


sealed class LoadableData<out T> {
    abstract val data: T?

    data class Loaded<T>(override val data: T) : LoadableData<T>()

    data class Failed<T>(val exception: Exception) :
        LoadableData<T>() {
        override val data: T? = null
    }

    data class Loading<T>(val additionalInfo: Any? = null) : LoadableData<T>() {
        override val data: T? = null
    }

    data object NotLoaded : LoadableData<Nothing>() {
        override val data: Nothing? = null
    }
}

fun <T> MutableStateFlow<LoadableData<T>>.setAsLoadableData(data: T?) {
    if (data == null) return
    value = try {
        LoadableData.Loaded(data)
    } catch (e: Exception) {
        LoadableData.Failed(e)
    }
}