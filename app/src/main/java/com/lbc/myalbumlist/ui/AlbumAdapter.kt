package com.lbc.myalbumlist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.lbc.myalbumlist.databinding.ItemListAlbumBinding
import com.lbc.myalbumlist.repo.data.model.AlbumEntity


class AlbumAdapter: ListAdapter<AlbumEntity, AlbumAdapter.AlbumViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(ItemListAlbumBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        ))
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    inner class AlbumViewHolder(private val binding: ItemListAlbumBinding):
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

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AlbumEntity>() {
            override fun areItemsTheSame(oldItem: AlbumEntity, newItem: AlbumEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: AlbumEntity, newItem: AlbumEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}