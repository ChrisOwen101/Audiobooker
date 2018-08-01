package com.marche.audiobookier.common.injection.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import com.marche.audiobookier.data.DataManager
import com.marche.audiobookier.data.remote.RemoteApi
import com.marche.audiobookier.injection.ApplicationContext
import org.mockito.Mockito.mock
import javax.inject.Singleton

/**
 * Provides application-level dependencies for an app running on a testing environment
 * This allows injecting mocks if necessary.
 */
@Module
class ApplicationTestModule(private val application: Application) {

    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return application
    }

    /*************
     * MOCKS
     */

    @Provides
    @Singleton
    internal fun providesDataManager(): DataManager {
        return mock(DataManager::class.java)
    }

    @Provides
    @Singleton
    internal fun provideMvpBoilerplateService(): RemoteApi {
        return mock(RemoteApi::class.java)
    }

}
