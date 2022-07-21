package ru.turbopro.photosviewer.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.turbopro.photosviewer.models.PhotoModel

@Database(entities = [PhotoModel::class], version = 1)
abstract class PhotosDB : RoomDatabase() {
    abstract fun photosDao(): PhotosDao
}