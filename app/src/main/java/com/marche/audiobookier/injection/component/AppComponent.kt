package com.marche.audiobookier.injection.component

import android.app.Application
import android.content.Context
import com.marche.audiobookier.data.DataManager
import com.marche.audiobookier.data.remote.RemoteApi
import com.marche.audiobookier.injection.ApplicationContext
import com.marche.audiobookier.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun dataManager(): DataManager

    fun remoteApi(): RemoteApi
}
