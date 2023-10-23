package fr.leboncoin.bcalbums.feature_albums.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import fr.leboncoin.bcalbums.R
import fr.leboncoin.bcalbums.common.util.UIEvent
import fr.leboncoin.bcalbums.common.util.ViewState.DataState
import fr.leboncoin.bcalbums.common.util.ViewState.LoadingState
import fr.leboncoin.bcalbums.common.util.collectLifecycleAware
import fr.leboncoin.bcalbums.common.util.toast
import fr.leboncoin.bcalbums.feature_albums.presentation.adapters.AlbumsRecyclerAdapter
import fr.leboncoin.bcalbums.feature_albums.presentation.views.MainView

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var mainView: MainView
    private val viewModel by viewModels<MainFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_layout, container, false)?.apply {
            mainView = this as MainView
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        mainView.albumRecyclerAdapter = AlbumsRecyclerAdapter { album ->
            Toast.makeText(activity, album.title, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupObservers() {
        viewModel.eventFlow.collectLifecycleAware(viewLifecycleOwner) { sharedEvent ->
            when (sharedEvent) {
                is UIEvent.ShowError -> context.toast(getText(sharedEvent.errorStrRes))
            }
        }
        viewModel.stateFlow.collectLifecycleAware(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is LoadingState -> Unit
                is DataState -> viewState.data?.let { albums ->
                    mainView.setProgressVisible(false)
                    mainView.albumRecyclerAdapter.submitList(albums)
                }
            }
        }
    }
}