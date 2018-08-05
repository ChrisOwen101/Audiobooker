package com.marche.audiobookier.features.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.animation.DecelerateInterpolator
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.model.MediaFile
import com.marche.audiobookier.R
import com.marche.audiobookier.data.model.AudiobookEntry
import com.marche.audiobookier.features.base.BaseActivity
import com.marche.audiobookier.util.FilePicker
import com.marche.audiobookier.util.ViewUtil
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainMvpView {

    @Inject
    lateinit var mainPresenter: MainPresenter

    private val FILE_REQUEST_CODE = 111

    private val layoutManager = LinearLayoutManager(this)

    override fun layoutId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        mainPresenter.attachView(this)

        setSupportActionBar(tToolbar)

        fabAddAudiobook.setOnClickListener { mainPresenter.onFABClicked() }
        sheetHeader.setOnClickListener { mainPresenter.toggleBottomSheet() }

        BottomSheetBehavior
                .from(llBottomSheet)
                .setBottomSheetCallback(mainPresenter.bottomSheetCallback)

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
        rvRecyclerView.layoutManager = layoutManager
        rvRecyclerView.adapter = adapter
    }

    override fun navigateToFilePickerActivity() {
        startActivityForResult(FilePicker.getFilePickerIntent(this), FILE_REQUEST_CODE)
    }

    override fun onBottomSheetSlide(offset: Float) {
        fabAddAudiobook.animate().scaleX(1 - offset).scaleY(1 - offset).setDuration(0).start()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        mainPresenter.onMenuClicked(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    override fun showBackdrop(show: Boolean) {
        rvRecyclerView.animate().setDuration(400).y(if(show) 500f else ViewUtil.getActionBarHeight(this)).setInterpolator(DecelerateInterpolator()).start()
    }

    override fun showBottomSheet(show: Boolean) {
        BottomSheetBehavior.from(llBottomSheet).state = if(show) {
            BottomSheetBehavior.STATE_EXPANDED
        } else {
            BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.detachView()
    }
}