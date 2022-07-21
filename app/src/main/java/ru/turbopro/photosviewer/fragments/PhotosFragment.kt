package ru.turbopro.photosviewer.fragments

import ru.turbopro.photosviewer.adapters.GridPhotosAdapter
import ru.turbopro.photosviewer.adapters.interfaces.PhotosListener
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import ru.turbopro.photosviewer.R
import kotlinx.android.synthetic.main.photos_grid_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.turbopro.photosviewer.models.PhotoModel
import ru.turbopro.photosviewer.utils.NavigationUtils
import ru.turbopro.photosviewer.viewmodels.PhotosGridViewModel

class PhotosFragment : Fragment(), PhotosListener {

    private lateinit var viewModel: PhotosGridViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View? {
        return inflater.inflate(R.layout.photos_grid_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observe()
        viewModel.requestPhotos()
    }

    private fun init() {
        viewModel = ViewModelProvider(this).get(PhotosGridViewModel::class.java)
        setHasOptionsMenu(true)
    }

    private fun observe() {
        viewModel.photosRequestObserver.observe(this, Observer { onPhotosReceived(it) })
    }

    private fun onPhotosReceived(photos: List<PhotoModel>) {
        loadingIndicator.visibility = View.GONE
        if (photos.isEmpty()) {
            showSnackbar(getString(R.string.connection_issue))
            return
        }

        // init recycler
        photosGrid.layoutManager = GridLayoutManager(context, 2)
        photosGrid.adapter = GridPhotosAdapter(photos, this)
        onResume()
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(rootLayout, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        NavigationUtils.showFragment(FavoritePhotosFragment(), true, requireActivity())
        return super.onOptionsItemSelected(item)
    }

    override fun onPhotoClicked(photoModel: PhotoModel) {
        NavigationUtils.showFragment(PhotoViewFragment(photoModel), true, requireActivity())
    }

    override fun onResume() {
        super.onResume()
        requireActivity().setTitle(R.string.app_name)
        photosGrid.adapter?.let {
            CoroutineScope(IO).launch {
                withContext(Main) { (it as GridPhotosAdapter).checkFavorite() }
            }
        }
    }
}