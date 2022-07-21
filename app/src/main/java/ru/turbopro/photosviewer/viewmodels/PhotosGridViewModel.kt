package ru.turbopro.photosviewer.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.turbopro.photosviewer.app.App
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.turbopro.photosviewer.models.PhotoModel

class PhotosGridViewModel : ViewModel() {


    val photosRequestObserver: MutableLiveData<List<PhotoModel>> = MutableLiveData()
    private var disposable: Disposable? = null

    fun requestPhotos() {
        disposable = App.api.getPhotosList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onPhotosReceived, this::onPhotosRequestFailed)
    }

    private fun onPhotosReceived(photos: List<PhotoModel>) {
        photosRequestObserver.value = photos
    }

    private fun onPhotosRequestFailed(error: Throwable) {
        photosRequestObserver.value = ArrayList()
        error.printStackTrace()
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

}