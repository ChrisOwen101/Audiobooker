package com.marche.audiobookier.features.main

import com.marche.audiobookier.data.model.AudiobookEntry
import com.marche.audiobookier.features.base.MvpView

interface MainMvpView : MvpView {

    fun navigateToFilePickerActivity()

    fun onBottomSheetSlide(offset: Float)

    fun onAudiobooksUpdated(list: List<AudiobookEntry>)

    fun showBackdrop(show: Boolean)
}