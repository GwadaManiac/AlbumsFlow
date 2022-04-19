package com.lbc.myalbumlist.repo.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lbc.myalbumlist.model.Album
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Albums")
data class AlbumEntity(

    @PrimaryKey
    var id: Int,

    @ColumnInfo
    var albumId: Int,

    @ColumnInfo
    var title: String,

    @ColumnInfo
    var url: String,

    @ColumnInfo
    var thumbnailUrl: String
): Parcelable {
    fun toAlbumModel() = Album(id, albumId, title, url, thumbnailUrl)
}
