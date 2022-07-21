package ru.turbopro.photosviewer.viewholders

import ru.turbopro.photosviewer.adapters.interfaces.PhotosListener
import android.annotation.SuppressLint
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.turbopro.photosviewer.app.App
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.photo_card.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import ru.turbopro.photosviewer.models.PhotoModel
import ru.turbopro.photosviewer.room.PhotosDao
import ru.turbopro.photosviewer.utils.ImageUtils


class GridPhotosViewHolder(itemView: View, private val listener: PhotosListener) :
    RecyclerView.ViewHolder(itemView) {

    companion object {
        private val db: PhotosDao = App.db.photosDao()
    }

    private lateinit var photoModel: PhotoModel

    fun setData(photoModel: PhotoModel) {
        this.photoModel = photoModel
        setAuthor()
        setImage()
        setClickListener()
        setFavorite(photoModel)
    }

    private fun setFavorite(photoModel: PhotoModel) {
        photoModel.isFavorite?.let { itemView.favorite.isLiked = it }
        itemView.favorite.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) = onFavoriteAdded(photoModel)
            override fun unLiked(likeButton: LikeButton?) = onFavoriteRemoved(photoModel)
        })
        itemView.favorite_background.setOnClickListener { itemView.favorite.performClick() }
    }

    private fun setAuthor() {
        itemView.author.text = photoModel.author
    }

    private fun setImage() = ImageUtils.loadImage(photoModel.downloadUrl, itemView.image)

    private fun setClickListener() {
        itemView.setOnTouchListener(object : View.OnTouchListener {
            val gestureDetector = GestureDetector(
                App.instance.baseContext,
                object : GestureDetector.SimpleOnGestureListener() {
                    override fun onDoubleTap(e: MotionEvent?): Boolean {
                        onViewDoubleTapped()
                        return super.onDoubleTap(e)
                    }

                    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                        onViewSingleTapped()
                        return super.onSingleTapConfirmed(e)
                    }
                }
            )

            @SuppressLint("ClickableViewAccessibility")
            override fun onTouch(v: View?, event: MotionEvent): Boolean {
                gestureDetector.onTouchEvent(event)
                return true
            }
        })
    }

    private fun onViewSingleTapped() = listener.onPhotoClicked(photoModel)

    private fun onViewDoubleTapped() {
        itemView.favorite.performClick()
    }

    private fun onFavoriteAdded(photoModel: PhotoModel) {
        CoroutineScope(IO).launch { db.insertPhoto(photoModel) }
        photoModel.isFavorite = true
    }

    private fun onFavoriteRemoved(photoModel: PhotoModel) {
        CoroutineScope(IO).launch { db.deletePhoto(photoModel) }
        photoModel.isFavorite = false
    }

}