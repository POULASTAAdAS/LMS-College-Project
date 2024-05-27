package com.poulastaa.lms.presentation.apply_leave

import com.poulastaa.lms.data.model.auth.LocalUser
import com.poulastaa.lms.presentation.store_details.Holder
import com.poulastaa.lms.presentation.store_details.ListHolder

data class ApplyLeaveUiState(
    val isInternet: Boolean = false,
    val isMakingApiCall: Boolean = false,
    val user: LocalUser = LocalUser(),

    val isDocNeeded: Boolean = false,

    val balance: String = "0.0",

    val leaveType: ListHolder = ListHolder(),
    val dayType: ListHolder = ListHolder(
        all = listOf(
            "Full Day",
            "Half Day"
        ),
        selected = "Full Day"
    ),
    val fromDate: Holder = Holder(),
    val toDate: Holder = Holder(),
    val totalDays: String = "0",
    val leaveReason: Holder = Holder(),
    val addressDuringLeave: ListHolder = ListHolder(),
    val path: ListHolder = ListHolder()
)
