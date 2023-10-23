package fr.leboncoin.bcalbums.feature_albums.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.leboncoin.bcalbums.R
import fr.leboncoin.bcalbums.common.util.ErrorType.NETWORK_ERROR
import fr.leboncoin.bcalbums.common.util.Resource.Error
import fr.leboncoin.bcalbums.common.util.Resource.Loading
import fr.leboncoin.bcalbums.common.util.Resource.Success
import fr.leboncoin.bcalbums.common.util.UIEvent
import fr.leboncoin.bcalbums.common.util.ViewState
import fr.leboncoin.bcalbums.common.util.ViewState.DataState
import fr.leboncoin.bcalbums.common.util.ViewState.LoadingState
import fr.leboncoin.bcalbums.feature_albums.domain.model.Album
import fr.leboncoin.bcalbums.feature_albums.domain.usescases.GetAlbumsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val getAlbumsUseCase: GetAlbumsUseCase
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<ViewState<List<Album>>> by lazy {
        MutableStateFlow(DataState())
    }
    val stateFlow: StateFlow<ViewState<List<Album>>> = _stateFlow.asStateFlow()

    private val _eventFlow: MutableSharedFlow<UIEvent> by lazy {
        MutableSharedFlow()
    }
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        callGetAlbumsWebservice()
    }

    private fun callGetAlbumsWebservice() {
        viewModelScope.launch {
            getAlbumsUseCase().onEach { result ->
                when (result) {
                    is Loading -> _stateFlow.value = LoadingState()
                    is Success -> _stateFlow.value = DataState(result.data)
                    is Error -> {
                        when (result.dataError) {
                            NETWORK_ERROR -> {
                                _eventFlow.emit(
                                    UIEvent.ShowError(
                                        errorType = NETWORK_ERROR,
                                        errorStrRes = R.string.error_no_network
                                    )
                                )
                            }

                            else -> Unit
                        }
                    }
                }
            }.launchIn(this)
        }
    }
}