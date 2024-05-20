package com.poulastaa.lms.ui.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poulastaa.lms.data.model.auth.LocalUser
import com.poulastaa.lms.data.repository.utils.DataStoreRepository
import com.poulastaa.lms.navigation.Screens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun ViewModel.storeUser(
    ds: DataStoreRepository,
    localUser: LocalUser
) {
    viewModelScope.launch(Dispatchers.IO) {

    }
}

fun ViewModel.storeSignInState(state: Screens, ds: DataStoreRepository) {
    viewModelScope.launch(Dispatchers.IO) {
        ds.storeSignInState(state)
    }
}

fun ViewModel.storeCookie(cookie: String, ds: DataStoreRepository) {
    viewModelScope.launch(Dispatchers.IO) {

    }
}
