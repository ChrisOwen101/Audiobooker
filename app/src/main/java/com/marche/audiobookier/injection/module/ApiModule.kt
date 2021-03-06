package com.marche.audiobookier.injection.module

import dagger.Module
import dagger.Provides
import com.marche.audiobookier.data.remote.RemoteApi
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by shivam on 8/7/17.
 */
@Module(includes = arrayOf(NetworkModule::class))
class ApiModule {

    @Provides
    @Singleton
    internal fun providePokemonApi(retrofit: Retrofit): RemoteApi =
            retrofit.create(RemoteApi::class.java)
}