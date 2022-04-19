package com.lbc.myalbumlist.model

import android.os.Parcelable
import com.lbc.myalbumlist.repo.data.model.AlbumEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String

) : Parcelable {
    fun toAlbumEntity() = AlbumEntity(id, albumId, title, url, thumbnailUrl)
}
