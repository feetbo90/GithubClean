package com.example.githubappclean

import android.app.Application
import com.example.githubappclean.core.di.databaseModule
import com.example.githubappclean.core.di.networkModule
import com.example.githubappclean.core.di.repositoryModule
import com.example.githubappclean.di.useCaseModule
import com.example.githubappclean.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}