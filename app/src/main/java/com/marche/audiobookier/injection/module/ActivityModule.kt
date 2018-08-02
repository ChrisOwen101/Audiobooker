package com.marche.audiobookier.injection.module

import android.app.Activity
import android.content.Context
import com.marche.audiobookier.data.model.MyObjectBox
import com.marche.audiobookier.injection.ActivityContext
import com.marche.audiobookier.injection.ApplicationContext
import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    internal fun provideActivity(): Activity {
        return activity
    }

    @Provides
    @ActivityContext
    internal fun providesContext(): Context {
        return activity
    }


}
