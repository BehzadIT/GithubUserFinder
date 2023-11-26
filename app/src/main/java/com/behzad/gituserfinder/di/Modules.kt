package com.behzad.gituserfinder.di

import android.content.Context
import android.net.ConnectivityManager
import com.behzad.gituserfinder.features.shared.NoInternetConnectionException
import com.behzad.gituserfinder.features.user.data.GitHubApi
import com.behzad.gituserfinder.features.user.data.GithubUserRepository
import com.behzad.gituserfinder.features.user.detail.UserDetailViewModel
import com.behzad.gituserfinder.features.user.detail.usecase.GetGitHubUserDetailUseCase
import com.behzad.gituserfinder.features.user.search.UserSearchViewModel
import com.behzad.gituserfinder.features.user.search.usecase.SearchGitHubUsersUseCase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit


object Modules {
    val app = module {
        single { GithubUserRepository(get()) }
    }
    val useCases = module {
        factory { SearchGitHubUsersUseCase(get()) }
        factory { GetGitHubUserDetailUseCase(get()) }
    }
    val viewModels = module {
        viewModel { UserSearchViewModel(androidApplication(), get()) }
        viewModel { UserDetailViewModel(androidApplication(), get(), get()) }
    }

    val networkModule = module {
        single { provideOkHttpClient() }
        single { provideRetrofit(get()) }
        factory { provideForecastApi(get()) }
    }

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder().build()
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder().baseUrl("https://api.github.com").client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory(contentType)).build()
    }

    private fun provideForecastApi(retrofit: Retrofit): GitHubApi =
        retrofit.create(GitHubApi::class.java)

    class NetworkConnectionInterceptor(private val mContext: Context) :
        Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            if (!isConnected) {
                throw NoInternetConnectionException()
                // Throwing our custom exception 'NoConnectivityException'
            }
            val builder: Request.Builder = chain.request().newBuilder()
            return chain.proceed(builder.build())
        }
        val isConnected: Boolean
            get() {

                val connectivityManager =
                    mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val netInfo = connectivityManager.activeNetworkInfo
                return netInfo != null && netInfo.isConnected
            }
    }
}



