package com.example.githubappclean.core.di

import androidx.room.Room
import com.example.githubappclean.BuildConfig
import com.example.githubappclean.core.data.GithubRepository
import com.example.githubappclean.core.data.source.local.LocalDataSource
import com.example.githubappclean.core.data.source.local.room.GithubDatabase
import com.example.githubappclean.core.data.source.remote.RemoteDataSource
import com.example.githubappclean.core.data.source.remote.network.ApiService
import com.example.githubappclean.core.domain.repository.IGithubRepository
import com.example.githubappclean.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<GithubDatabase>().githubDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            GithubDatabase::class.java, "Github.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.MY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IGithubRepository> { GithubRepository(get(), get(), get()) }
}