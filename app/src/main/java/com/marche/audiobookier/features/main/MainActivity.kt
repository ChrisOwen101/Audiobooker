package com.marche.audiobookier.features.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.model.MediaFile
import com.marche.audiobookier.R
import com.marche.audiobookier.data.model.AudiobookEntry
import com.marche.audiobookier.features.audioservice.AudioService
import com.marche.audiobookier.features.audioservice.AudioServiceBinder
import com.marche.audiobookier.features.base.BaseActivity
import com.marche.audiobookier.features.common.RecyclerItemClickListener
import com.marche.audiobookier.util.FilePicker
import com.marche.audiobookier.util.ViewUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_player.*
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
        sheetHeader.setOnClickListener {
            mainPresenter.toggleBottomSheet()
        }

        rvRecyclerView.addOnItemTouchListener(
                RecyclerItemClickListener(this, rvRecyclerView, object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        startAudio((rvRecyclerView.adapter as AudiobookAdapter).getItems()[position].path)
                    }

                    override fun onLongItemClick(view: View, position: Int) {

                    }
                })
        )

        rvRecyclerView.setOnTouchListener { _, _ ->
            mainPresenter.showBackDrop(false)
            false
        }

        BottomSheetBehavior
                .from(pvBottomSheet)
                .setBottomSheetCallback(mainPresenter.bottomSheetCallback)

        mainPresenter.getAudiobooks()

        bindAudioService()
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            // Cast and assign background service's onBind method returned iBander object.
            getAudioServiceBinder().setService(iBinder as AudioServiceBinder)
        }

        override fun onServiceDisconnected(componentName: ComponentName) {

        }
    }

    fun getAudioServiceBinder(): AudioBindable{
        return (pvBottomSheet as AudioBindable)
    }

    private fun bindAudioService() {
        if (getAudioServiceBinder().getService() == null) {
            val intent = Intent(this, AudioService::class.java)
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    private fun unbindAudioService() {
        if (getAudioServiceBinder().getService() != null) {
            unbindService(serviceConnection)
        }
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
        rvRecyclerView.animate().scaleX(1 - (offset/10)).scaleY(1 - (offset/10)).setDuration(0).start()
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
        BottomSheetBehavior.from(pvBottomSheet).state = if(show) {
            BottomSheetBehavior.STATE_EXPANDED
        } else {
            BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    fun startAudio(url: String){
        getAudioServiceBinder().getService()?.audioFileUrl = url

        // Web audio is a stream audio.
        getAudioServiceBinder().getService()?.isStreamAudio = true

        // Set application context.
        getAudioServiceBinder().getService()?.context = applicationContext

        // Start audio in background service.
        getAudioServiceBinder().getService()?.startAudio()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.detachView()
        unbindAudioService()
    }
}