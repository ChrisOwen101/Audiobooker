package com.marche.audiobookier.features.main

import android.support.design.widget.BottomSheetBehavior
import android.view.View
import com.marche.audiobookier.data.DataManager
import com.marche.audiobookier.features.base.BasePresenter
import com.marche.audiobookier.injection.ConfigPersistent
import javax.inject.Inject

@ConfigPersistent
class MainPresenter @Inject
constructor(private val dataManager: DataManager) : BasePresenter<MainMvpView>() {

    val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {}

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            checkViewAttached()
            mvpView?.onBottomSheetSlide(slideOffset)        }
    }

    fun getAudiobooks() {
        checkViewAttached()
    }

    fun onFABClicked() {
        checkViewAttached()
        mvpView?.navigateToFilePickerActivity()
    }
}