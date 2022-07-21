package ru.turbopro.photosviewer.adapters.interfaces

import android.view.View

interface FavoriteRemovedListener {
    fun favoriteRemoved(position: Int, view: View)
}