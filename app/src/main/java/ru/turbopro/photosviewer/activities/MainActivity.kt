package ru.turbopro.photosviewer.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.turbopro.photosviewer.R
import ru.turbopro.photosviewer.fragments.PhotosFragment
import ru.turbopro.photosviewer.utils.NavigationUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NavigationUtils.showFragment(PhotosFragment(), false, this)
    }

    override fun onResume() {
        super.onResume()
        supportFragmentManager.addOnBackStackChangedListener {
            val fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.rootLayout)
            fragment?.onResume()
        }
    }
}