package com.marche.audiobookier.features.main

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import com.marche.audiobookier.R
import com.marche.audiobookier.features.base.BaseActivity
import com.marche.audiobookier.util.FilePicker
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

        val bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet)
        bottomSheetBehavior.setBottomSheetCallback(mainPresenter.bottomSheetCallback)
    }

    override fun navigateToFilePickerActivity() {
        startActivityForResult(FilePicker.getFilePickerIntent(this), 11)
    }

    override fun onBottomSheetSlide(offset: Float) {
        fabAddAudiobook.animate().scaleX(1 - offset).scaleY(1 - offset).setDuration(0).start()
    }

    override fun layoutId() = R.layout.activity_main

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.detachView()
    }
}