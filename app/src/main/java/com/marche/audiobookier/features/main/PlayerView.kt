package com.marche.audiobookier.features.main

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.marche.audiobookier.R
import com.marche.audiobookier.features.audioservice.AudioServiceBinder

class PlayerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr), AudioBindable {

    private var audioServiceBinder: AudioServiceBinder? = null

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_player, this, true)

        orientation = VERTICAL
    }

    override fun setService(audioBindable: AudioServiceBinder) {
        audioServiceBinder = audioBindable
    }

    override fun getService(): AudioServiceBinder? {
        return audioServiceBinder
    }

}