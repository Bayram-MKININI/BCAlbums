package fr.leboncoin.bcalbums.controllers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import fr.leboncoin.bcalbums.model.Album
import fr.leboncoin.bcalbums.model.DataRepository
import kotlinx.coroutines.launch
import fr.leboncoin.bcalbums.utils.Error
import fr.leboncoin.bcalbums.utils.NetworkHelper
import fr.leboncoin.bcalbums.utils.Resource

class MainFragmentViewModel(
    application: Application,
    private val dataRepository: DataRepository,
    private val networkHelper: NetworkHelper
) : AndroidViewModel(application) {

    private val _albums = MutableLiveData<Resource<List<Album>>>()
    val albums: LiveData<Resource<List<Album>>>
        get() = _albums

    init {
        callGetAlbumsWebservice()
    }

    private fun callGetAlbumsWebservice() {

        viewModelScope.launch {

            if (networkHelper.isNetworkConnected()) {

                dataRepository.fetchAlbums().collect {
                    _albums.value = it
                }

            } else
                _albums.value = Resource.error(error = Error.NETWORK)
        }
    }
}