package ru.turbopro.photosviewer.room

import androidx.room.*
import ru.turbopro.photosviewer.models.PhotoModel

@Dao
interface PhotosDao {
    @Query("SELECT * FROM FavoritePhotos")
    suspend fun getFavoritePhotos(): List<PhotoModel>

    @Query("SELECT * FROM FavoritePhotos WHERE id=(:id)")
    suspend fun getPhoto(id: Int): PhotoModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoto(vararg photo: PhotoModel)

    @Delete
    suspend fun deletePhoto(photo: PhotoModel): Int?

}