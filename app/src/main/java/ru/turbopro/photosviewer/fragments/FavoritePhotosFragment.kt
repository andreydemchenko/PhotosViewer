package ru.turbopro.photosviewer.fragments

import ru.turbopro.photosviewer.adapters.FavoritePhotosAdapter
import ru.turbopro.photosviewer.adapters.interfaces.PhotosListener
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.turbopro.photosviewer.R
import kotlinx.android.synthetic.main.favorite_photos_fragment.*
import ru.turbopro.photosviewer.models.PhotoModel
import ru.turbopro.photosviewer.utils.NavigationUtils
import ru.turbopro.photosviewer.viewmodels.FavoritePhotosViewModel

class FavoritePhotosFragment : Fragment(), PhotosListener {

    private lateinit var viewModel: FavoritePhotosViewModel
    private lateinit var adapter: FavoritePhotosAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View? {
        init()
        observeChanges()
        return inflater.inflate(R.layout.favorite_photos_fragment, container, false)
    }

    private fun observeChanges() {
        viewModel.listObserver.observe(this, Observer { list ->
            emptyState.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            photosList.layoutManager = LinearLayoutManager(context)
            adapter.setPhotos(list)
            photosList.adapter = adapter
        })

        adapter.listEmptyState.observe(
            this,
            Observer { if (it) emptyState.visibility = View.VISIBLE })
    }

    private fun init() {
        setHasOptionsMenu(true)
        requireActivity().setTitle(R.string.favorite_photos_title)
        adapter = FavoritePhotosAdapter(requireActivity(), this)
        viewModel = ViewModelProvider(this).get(FavoritePhotosViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.getItem(0).isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPhotoClicked(photoModel: PhotoModel) {
        NavigationUtils.showFragment(PhotoViewFragment(photoModel), true, requireActivity())
    }

    override fun onResume() {
        super.onResume()
        viewModel.requestFavoritePhotos()
    }
}