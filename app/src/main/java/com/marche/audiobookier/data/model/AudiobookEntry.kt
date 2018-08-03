package com.marche.audiobookier.data.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.util.*

@Entity
data class AudiobookEntry(
        @Id var id: Long = 0,
        val name: String,
        val path: String)