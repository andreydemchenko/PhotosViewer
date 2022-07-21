package ru.turbopro.photosviewer.adapters

import ru.turbopro.photosviewer.adapters.interfaces.PhotosListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.turbopro.photosviewer.app.App
import ru.turbopro.photosviewer.R
import ru.turbopro.photosviewer.models.PhotoModel
import ru.turbopro.photosviewer.utils.cloneMutableList
import ru.turbopro.photosviewer.viewholders.GridPhotosViewHolder

class GridPhotosAdapter(
    private var photos: List<PhotoModel>,
    private val listener: PhotosListener
) :
    RecyclerView.Adapter<GridPhotosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridPhotosViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.photo_card, parent, false)
        return GridPhotosViewHolder(view, listener)
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: GridPhotosViewHolder, i: Int) = holder.setData(photos[i])

    suspend fun checkFavorite() {
        val oldList = photos
        val newList = photos.cloneMutableList()
        for (photo in newList) {
            photo.isFavorite = App.db.photosDao().getPhoto(photo.id) != null
        }

        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            PhotosDiffCallback(oldList, newList)
        )

        this.photos = newList
        diffResult.dispatchUpdatesTo(this)
    }
}




