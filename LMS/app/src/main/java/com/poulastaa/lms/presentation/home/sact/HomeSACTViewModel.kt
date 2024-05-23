package com.poulastaa.lms.presentation.home.sact

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poulastaa.lms.domain.repository.utils.ConnectivityObserver
import com.poulastaa.lms.domain.repository.utils.DataStoreRepository
import com.poulastaa.lms.presentation.home.HomeUiAction
import com.poulastaa.lms.presentation.utils.fileFromUri
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeSACTViewModel @Inject constructor(
    private val network: ConnectivityObserver,
    private val ds: DataStoreRepository,
) : ViewModel() {
    var state by mutableStateOf(HomeSACTUiState())
        private set

    init {
        viewModelScope.launch {
            network.observe().collectLatest {
                state = if (it == ConnectivityObserver.NetworkStatus.AVAILABLE) state.copy(
                    isInternet = true
                ) else state.copy(
                    isInternet = false
                )
            }
        }
    }

    private val _uiEvent = Channel<HomeUiAction>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onEvent(event: HomeSACTUiEvent) {
        when (event) {
            HomeSACTUiEvent.OnApplyLeaveClick -> {

            }

            HomeSACTUiEvent.OnLeaveHistoryClick -> {

            }

            HomeSACTUiEvent.OnLeaveStatusClick -> {

            }

            is HomeSACTUiEvent.OnProfilePicClick -> {
                if (event.url == null) return

                val file = fileFromUri(event.context, event.url)

                // todo send to backend
            }
        }
    }
}