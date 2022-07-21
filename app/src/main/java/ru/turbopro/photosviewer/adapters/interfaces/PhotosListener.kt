package ru.turbopro.photosviewer.adapters.interfaces

import ru.turbopro.photosviewer.models.PhotoModel

interface PhotosListener {
    fun onPhotoClicked(photoModel: PhotoModel)
}