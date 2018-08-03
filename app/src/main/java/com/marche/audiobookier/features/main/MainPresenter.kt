package com.marche.audiobookier.features.main

import android.support.design.widget.BottomSheetBehavior
import android.view.View
import com.jaiselrahman.filepicker.model.MediaFile
import com.marche.audiobookier.data.DataManager
import com.marche.audiobookier.data.local.LocalRepository
import com.marche.audiobookier.data.model.AudiobookEntry
import com.marche.audiobookier.features.base.BasePresenter
import com.marche.audiobookier.injection.ConfigPersistent
import javax.inject.Inject

@ConfigPersistent
class MainPresenter @Inject
constructor(private val dataManager: DataManager, private val localRepository: LocalRepository) : BasePresenter<MainMvpView>() {

    val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {}

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            checkViewAttached()
            mvpView?.onBottomSheetSlide(slideOffset)        }
    }

    fun getAudiobooks() {
        checkViewAttached()
        mvpView?.onAudiobooksUpdated(localRepository.getAllAudiobookEntries())
    }

    fun onFABClicked() {
        checkViewAttached()
        mvpView?.navigateToFilePickerActivity()
    }

    fun onNewAudiobooksChosen(files : List<MediaFile>){
        checkViewAttached()

        val entries = files.map {
            AudiobookEntry(name=it.name,  path=it.path)
        }

        entries.forEach { localRepository.storeAudiobookEntry(it) }
        mvpView?.onAudiobooksUpdated(localRepository.getAllAudiobookEntries())
    }
}