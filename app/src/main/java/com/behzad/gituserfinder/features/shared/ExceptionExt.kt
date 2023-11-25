package com.behzad.gituserfinder.features.shared

import android.content.Context
import com.behzad.gituserfinder.R
import okio.IOException
import retrofit2.HttpException

suspend fun <T> tryWithCustomExceptions(action: suspend () -> T): T {
    return try {
        action()
    } catch (e: HttpException) {
        if (e.code() >= 400 && e.code() > 500) throw HttpClientError()
        else if (e.code() >= 500) throw HttpServerError()
        else throw e
    } catch (e: IOException) {
        throw NoConnectivityException()
    }
}

fun Exception.getErrorMessage(context: Context): String {
    return when (this) {
        is NoConnectivityException -> context.getString(R.string.error_connection_issue)
        is HttpClientError -> context.getString(R.string.error_http_client)
        is HttpServerError -> context.getString(R.string.error_http_server)
        else -> context.getString(R.string.error_http_unknown)
    }
}