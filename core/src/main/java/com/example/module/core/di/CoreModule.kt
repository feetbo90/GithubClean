package com.example.module.core.di

import androidx.room.Room
import com.example.module.core.BuildConfig
import com.example.module.core.data.GithubRepository
import com.example.module.core.data.source.local.LocalDataSource
import com.example.module.core.data.source.local.room.GithubDatabase
import com.example.module.core.data.source.remote.RemoteDataSource
import com.example.module.core.data.source.remote.network.ApiService
import com.example.module.core.domain.repository.IGithubRepository
import com.example.module.core.utils.AppExecutors
import com.example.module.core.utils.DataMapper
import com.example.module.core.utils.UserImageLoader
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
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
        val passphrase: ByteArray = SQLiteDatabase.getBytes(BuildConfig.MY_KEY_DB.toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            GithubDatabase::class.java, "Github.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = "api.github.com"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/GyhWVHsOXNZc6tGTNd15kXF9YD0kEZaGxYn6MUva5jY=")
            .add(hostname, "sha256/1EkvzibgiE3k+xdsv+7UU5vhV8kdFCQiUiFdMX5Guuk=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
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
    single { DataMapper }
    single { UserImageLoader() }

    factory { AppExecutors() }
    single<IGithubRepository> { GithubRepository(get(), get(), get(), get()) }
}