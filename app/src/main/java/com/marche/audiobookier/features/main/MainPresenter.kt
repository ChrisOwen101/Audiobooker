package com.marche.audiobookier.features.main

import com.marche.audiobookier.data.DataManager
import com.marche.audiobookier.features.base.BasePresenter
import com.marche.audiobookier.injection.ConfigPersistent
import javax.inject.Inject

@ConfigPersistent
class MainPresenter @Inject
constructor(private val dataManager: DataManager) : BasePresenter<MainMvpView>() {

    fun getAudiobooks(limit: Int) {
        checkViewAttached()

    }
}