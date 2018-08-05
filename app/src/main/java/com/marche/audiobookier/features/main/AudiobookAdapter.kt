package com.marche.audiobookier.features.main

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marche.audiobookier.R
import com.marche.audiobookier.data.model.AudiobookEntry
import kotlinx.android.synthetic.main.audiobook_list_item.view.*

class AudiobookAdapter(private val items : List<AudiobookEntry>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    fun getItems(): List<AudiobookEntry>{
        return items
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.audiobook_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = items[position].name
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val tvTitle = view.tvTitle!!
}