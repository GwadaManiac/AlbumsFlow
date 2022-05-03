package com.lbc.myalbumlist.ui

import android.webkit.WebSettings
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.lbc.myalbumlist.databinding.ItemListAlbumBinding
import com.lbc.myalbumlist.repo.data.model.AlbumEntity

class AlbumViewHolder(private val binding: ItemListAlbumBinding):
    RecyclerView.ViewHolder(binding.root) {
    fun bind(album: AlbumEntity) {
        binding.title.text = album.title

        val glideUrl = GlideUrl(album.thumbnailUrl, LazyHeaders.Builder()
            .addHeader("User-Agent", WebSettings.getDefaultUserAgent(binding.root.context))
            .build())
        Glide.with(binding.root.context)
            .load(glideUrl)
            .into(binding.image)
    }
}