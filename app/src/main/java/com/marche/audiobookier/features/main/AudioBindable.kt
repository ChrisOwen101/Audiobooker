package com.marche.audiobookier.features.main

import com.marche.audiobookier.features.audioservice.AudioServiceBinder

interface AudioBindable{

    fun getService(): AudioServiceBinder?

    fun setService(audioBindable: AudioServiceBinder)
}