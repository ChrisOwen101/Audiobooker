package com.marche.audiobookier.util

import android.app.Activity
import android.content.Intent
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations

object FilePicker {

    fun getFilePickerIntent(activity: Activity) : Intent{
        return Intent(activity, FilePickerActivity::class.java).apply {
            putExtra(FilePickerActivity.CONFIGS, Configurations.Builder()
                    .setCheckPermission(true)
                    .setShowAudios(true)
                    .setShowFiles(true)
                    .setShowImages(false)
                    .setShowVideos(false)
                    .setMaxSelection(1)
                    .setSkipZeroSizeFiles(true)
                    .setSuffixes("mp3","m4b")
                    .build())
        }
    }
}