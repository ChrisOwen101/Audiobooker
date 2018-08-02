package com.marche.audiobookier.injection.module

import android.app.Application
import android.content.Context
import com.marche.audiobookier.AudiobookerApplication
import com.marche.audiobookier.data.local.LocalRepository
import dagger.Module
import dagger.Provides
import com.marche.audiobookier.injection.ApplicationContext

@Module(includes = arrayOf(ApiModule::class))
class AppModule(private val application: Application) {

    @Provides
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return application
    }

    @Provides
    internal fun provideLocalRepository(): LocalRepository {
        return (application as AudiobookerApplication).localRepository
    }
}