package fr.leboncoin.bcalbums.presenters

import androidx.lifecycle.*
import fr.leboncoin.bcalbums.model.Album
import fr.leboncoin.bcalbums.model.DataRepository
import kotlinx.coroutines.launch
import fr.leboncoin.bcalbums.utils.Error
import fr.leboncoin.bcalbums.utils.NetworkHelper
import fr.leboncoin.bcalbums.utils.Resource

class MainFragmentViewModel(
    private val dataRepository: DataRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

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

                    it.data?.let { albums ->
                        if (albums.isNotEmpty())
                            _albums.value = it
                    }
                }

            } else
                _albums.value = Resource.error(error = Error.NETWORK)
        }
    }
}