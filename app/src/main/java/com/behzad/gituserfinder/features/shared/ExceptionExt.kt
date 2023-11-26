package com.behzad.gituserfinder.features.shared

import android.content.Context
import com.behzad.gituserfinder.R
import okio.IOException
import retrofit2.HttpException

suspend fun <T> tryWithCustomExceptions(action: suspend () -> T): T {
    return try {
        action()
    } catch (e: IOException) {
        throw NoInternetConnectionException()
    } catch (e: HttpException) {
        if (e.code() in 400..499) throw HttpClientException()
        else if (e.code() >= 500) throw HttpServerException()
        else throw e
    } catch (e: Exception) {
        throw e
    }
}

fun Exception.getErrorMessage(context: Context): String {
    return when (this) {
        is NoInternetConnectionException -> context.getString(R.string.error_connection_issue)
        is HttpClientException -> context.getString(R.string.error_http_client)
        is HttpServerException -> context.getString(R.string.error_http_server)
        else -> context.getString(R.string.error_http_unknown)
    }
}