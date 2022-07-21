package ru.turbopro.photosviewer.app

import android.app.Application
import androidx.room.Room
import ru.turbopro.photosviewer.api.PhotosAPI
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.turbopro.photosviewer.room.PhotosDB

class App : Application() {

    companion object {
        lateinit var api: PhotosAPI
        lateinit var db: PhotosDB
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        initAPI()
        initDB()
        instance = this
    }

    private fun initAPI() {
        val retrofit = Retrofit.Builder().apply {
            baseUrl(PhotosAPI.apiBaseURL)
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        }.build()

        api = retrofit.create(PhotosAPI::class.java)
    }

    private fun initDB() {
        db = Room.databaseBuilder(
            this,
            PhotosDB::class.java, "local-database"
        ).build()
    }
}