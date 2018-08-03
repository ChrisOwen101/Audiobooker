package com.marche.audiobookier.features.main

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.BottomSheetBehavior
import com.marche.audiobookier.R
import com.marche.audiobookier.data.local.LocalRepository
import com.marche.audiobookier.features.base.BaseActivity
import com.marche.audiobookier.util.FilePicker
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.model.MediaFile
import timber.log.Timber


class MainActivity : BaseActivity(), MainMvpView {

    @Inject
    lateinit var mainPresenter: MainPresenter

    @Inject
    lateinit var localRepository: LocalRepository

    val FILE_REQUEST_CODE = 111

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        mainPresenter.attachView(this)

        setSupportActionBar(tToolbar)

        fabAddAudiobook.setOnClickListener { mainPresenter.onFABClicked() }

        val bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet)
        bottomSheetBehavior.setBottomSheetCallback(mainPresenter.bottomSheetCallback)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            FILE_REQUEST_CODE -> {
                val files : List<MediaFile> = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES)
                Timber.log(10, files.toString())
            }
        }
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