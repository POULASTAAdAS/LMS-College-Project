package com.poulastaa.lms.presentation.apply_leave

import com.poulastaa.lms.navigation.Screens
import com.poulastaa.lms.ui.utils.UiText

sealed interface ApplyLeaveUiAction {
    data class OnSuccess(val screen :Screens): ApplyLeaveUiAction
    data class Err(val message: UiText): ApplyLeaveUiAction
}