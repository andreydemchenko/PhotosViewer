package ru.turbopro.photosviewer.utils

import android.content.Context
import android.widget.ImageView
import ru.turbopro.photosviewer.R
import com.squareup.picasso.Picasso
import com.stfalcon.imageviewer.StfalconImageViewer

object ImageUtils {

    fun loadImage(url: String, img: ImageView) {
        Picasso.get()
            .load(url)
            .fit()
            .centerCrop()
            .placeholder(R.color.grey_purple)
            .into(img)
    }

    fun viewFullScreenImage(context: Context, url: String) {
        StfalconImageViewer.Builder(context, arrayOf(url)) { img, url ->
            Picasso.get()
                .load(url)
                .fit()
                .centerInside()
                .placeholder(R.color.black)
                .into(img)
        }.show()
    }

}