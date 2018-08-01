package com.marche.audiobookier.data

import com.marche.audiobookier.data.remote.RemoteApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject
constructor(private val remoteApi: RemoteApi) {


}