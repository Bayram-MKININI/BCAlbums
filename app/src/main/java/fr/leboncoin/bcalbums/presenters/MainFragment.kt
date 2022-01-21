package fr.leboncoin.bcalbums.presenters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fr.leboncoin.bcalbums.utils.inflate
import fr.leboncoin.bcalbums.views.AlbumItemView.AlbumItemViewAdapter
import fr.leboncoin.bcalbums.views.MainView
import fr.leboncoin.bcalbums.views.MainView.MainViewCallback
import android.widget.Toast
import fr.leboncoin.bcalbums.R
import fr.leboncoin.bcalbums.model.Album
import fr.leboncoin.bcalbums.utils.Error.*

import fr.leboncoin.bcalbums.utils.Status
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class MainFragment : Fragment() {

    private lateinit var mainView: MainView
    private val mainFragmentViewModel: MainFragmentViewModel by sharedViewModel()
    private val albumsList = mutableListOf<Album>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.main_layout, false)?.apply {
            mainView = this as MainView
            mainView.setMainViewCallback(mainViewCallback)
        }
    }

    private val mainViewCallback: MainViewCallback by lazy {
        object : MainViewCallback {
            override fun onItemClickedAtIndex(index: Int) {
                Toast.makeText(activity, albumsList[index].title, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        mainFragmentViewModel.albums.observe(
            viewLifecycleOwner, { resource ->

                when (resource.status) {

                    Status.SUCCESS -> {

                        mainView.setProgressVisible(false)

                        resource.data?.let { albums ->

                            if (albumsList.isNotEmpty())
                                albumsList.clear()

                            albumsList.addAll(albums)

                            refreshAdapters()
                        }
                    }

                    Status.LOADING -> {
                        mainView.setProgressVisible(true)
                    }

                    Status.ERROR -> {

                        mainView.setProgressVisible(false)

                        val message = when (resource.error) {
                            NETWORK -> getString(R.string.error_no_network)
                            SYSTEM -> getString(R.string.error_contact_support)
                            DATA -> getString(R.string.fetch_album_error)
                            NONE -> ""
                        }

                        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
                    }
                }
            })
    }

    private fun refreshAdapters() {

        if (albumsList.isEmpty())
            return

        val albumItemViewAdaptersList = albumsList.map { album ->

            AlbumItemViewAdapter().apply {
                thumbUrl = album.url
                title = album.title
            }
        }

        mainView.fillViewWithData(albumItemViewAdaptersList)
    }
}