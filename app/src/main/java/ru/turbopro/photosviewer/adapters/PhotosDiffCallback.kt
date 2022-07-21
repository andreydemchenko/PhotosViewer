package ru.turbopro.photosviewer.adapters

import androidx.recyclerview.widget.DiffUtil
import ru.turbopro.photosviewer.models.PhotoModel

class PhotosDiffCallback(
    private var oldList: List<PhotoModel>,
    private var newList: List<PhotoModel>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
