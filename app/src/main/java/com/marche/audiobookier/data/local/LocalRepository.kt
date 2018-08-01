package com.marche.audiobookier.data.local

import com.marche.audiobookier.data.model.AudiobookEntry
import io.objectbox.BoxStore
import javax.inject.Inject

class LocalRepository @Inject
constructor(val boxStore: BoxStore) {

    fun storeAudiobookEntry(audiobookEntry: AudiobookEntry){
        val audiobookBox = boxStore.boxFor(AudiobookEntry::class.java)
        audiobookBox.put(audiobookEntry)
    }

    fun getAllAudiobookEntries(): List<AudiobookEntry>{
        val audiobookBox = boxStore.boxFor(AudiobookEntry::class.java)
        return audiobookBox.all
    }

}