package com.marche.audiobookier.features.main

import android.support.design.widget.BottomSheetBehavior
import android.view.View
import com.jaiselrahman.filepicker.model.MediaFile
import com.marche.audiobookier.R
import com.marche.audiobookier.data.DataManager
import com.marche.audiobookier.data.local.LocalRepository
import com.marche.audiobookier.data.model.AudiobookEntry
import com.marche.audiobookier.features.base.BasePresenter
import com.marche.audiobookier.injection.ConfigPersistent
import javax.inject.Inject

@ConfigPersistent
class MainPresenter @Inject
constructor(private val dataManager: DataManager, private val localRepository: LocalRepository) : BasePresenter<MainMvpView>() {

    var expanded = false

    val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {}

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            checkViewAttached()
            mvpView?.onBottomSheetSlide(slideOffset)
            mvpView?.showBackdrop(false)
        }
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

        files.map {
            AudiobookEntry(name=it.name,  path=it.path)
        }.forEach {
            localRepository.storeAudiobookEntry(it)
        }

        mvpView?.onAudiobooksUpdated(localRepository.getAllAudiobookEntries())
    }

    fun onMenuClicked(id: Int) : Boolean{
        checkViewAttached()

        return if (id == R.id.action_favorite) {
            expanded = !expanded
            mvpView?.showBackdrop(expanded)
            true
        } else {
            false
        }
    }
}