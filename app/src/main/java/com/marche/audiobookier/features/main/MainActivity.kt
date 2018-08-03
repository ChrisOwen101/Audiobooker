package com.marche.audiobookier.features.main

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.marche.audiobookier.R
import com.marche.audiobookier.data.local.LocalRepository
import com.marche.audiobookier.features.base.BaseActivity
import com.marche.audiobookier.util.FilePicker
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.model.MediaFile
import com.marche.audiobookier.data.model.AudiobookEntry
import timber.log.Timber


class MainActivity : BaseActivity(), MainMvpView {

    @Inject
    lateinit var mainPresenter: MainPresenter

    private val FILE_REQUEST_CODE = 111

    override fun layoutId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        mainPresenter.attachView(this)

        setSupportActionBar(tToolbar)

        fabAddAudiobook.setOnClickListener { mainPresenter.onFABClicked() }

        val bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet)
        bottomSheetBehavior.setBottomSheetCallback(mainPresenter.bottomSheetCallback)

        mainPresenter.getAudiobooks()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            FILE_REQUEST_CODE -> {
                val files : List<MediaFile> = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES)
                mainPresenter.onNewAudiobooksChosen(files)
            }
        }
    }

    override fun onAudiobooksUpdated(list: List<AudiobookEntry>) {
        val adapter = AudiobookAdapter(list, this)
        rvRecyclerView.layoutManager = LinearLayoutManager(this)
        rvRecyclerView.adapter = adapter
    }

    override fun navigateToFilePickerActivity() {
        startActivityForResult(FilePicker.getFilePickerIntent(this), FILE_REQUEST_CODE)
    }

    override fun onBottomSheetSlide(offset: Float) {
        fabAddAudiobook.animate().scaleX(1 - offset).scaleY(1 - offset).setDuration(0).start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.detachView()
    }
}