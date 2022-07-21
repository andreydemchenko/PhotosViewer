package ru.turbopro.photosviewer.utils

import ru.turbopro.photosviewer.models.PhotoModel

fun List<PhotoModel>.cloneMutableList(): MutableList<PhotoModel> {
    val list: MutableList<PhotoModel> = mutableListOf()

    for (photo in this) list.add(
        PhotoModel(
            photo.id,
            photo.author,
            photo.width,
            photo.height,
            photo.url,
            photo.downloadUrl,
            photo.isFavorite
        )
    )

    return list
}