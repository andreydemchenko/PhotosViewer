package ru.turbopro.photosviewer.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "FavoritePhotos")
data class PhotoModel(
    @PrimaryKey @ColumnInfo(name = "id") @SerializedName("id") val id: Int,
    @ColumnInfo(name = "author") @SerializedName("author") val author: String,
    @ColumnInfo(name = "width") @SerializedName("width") val width: Int,
    @ColumnInfo(name = "height") @SerializedName("height") val height: Int,
    @ColumnInfo(name = "url") @SerializedName("url") val url: String,
    @ColumnInfo(name = "download_url") @SerializedName("download_url") val downloadUrl: String,
    var isFavorite: Boolean? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PhotoModel

        if (id != other.id) return false
        if (author != other.author) return false
        if (width != other.width) return false
        if (height != other.height) return false
        if (url != other.url) return false
        if (downloadUrl != other.downloadUrl) return false
        if (isFavorite != other.isFavorite) return false

        return true
    }

}