package com.behzad.gituserfinder.di

import com.behzad.gituserfinder.features.userSearch.UserSearchViewModel
import com.behzad.gituserfinder.features.userSearch.data.GitHubApi
import com.behzad.gituserfinder.features.userSearch.data.GithubUserRepository
import com.behzad.gituserfinder.features.userSearch.data.usecase.SearchGitHubUsersUseCase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
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
    }
    val viewModels = module {
        viewModel { UserSearchViewModel(androidApplication(), get()) }
    }

    val networkModule = module {
        factory { provideOkHttpClient() }
        factory { provideForecastApi(get()) }
        single { provideRetrofit(get()) }
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
}



