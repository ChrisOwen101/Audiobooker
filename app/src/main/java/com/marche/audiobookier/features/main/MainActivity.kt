package com.marche.audiobookier.features.main

import android.content.Intent
import android.os.Bundle
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import com.marche.audiobookier.R
import com.marche.audiobookier.features.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainMvpView {

    @Inject
    lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        mainPresenter.attachView(this)

        setSupportActionBar(tToolbar)

        fabAddAudiobook.setOnClickListener { mainPresenter.onFABClicked() }
    }

    override fun navigateToFilePickerActivity() {
        val intent = Intent(this, FilePickerActivity::class.java)
        intent.putExtra(FilePickerActivity.CONFIGS, Configurations.Builder()
                .setCheckPermission(true)
                .setShowAudios(true)
                .setShowFiles(true)
                .setShowImages(false)
                .setShowVideos(false)
                .setMaxSelection(1)
                .setSkipZeroSizeFiles(true)
                .setSuffixes("mp3","m4b")
                .build())
        startActivityForResult(intent, 11)
    }

    override fun layoutId() = R.layout.activity_main

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.detachView()
    }
}