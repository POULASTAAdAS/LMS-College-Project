package com.poulastaa.lms.data.model.auth

sealed class EndPoints(val route: String) {
    data object Auth : EndPoints(route = "/api/auth")

    data object SignUpEmailVerificationCheck :
        EndPoints(route = "/api/auth/signUpEmailVerificationCheck")

    data object LogInEmailVerificationCheck :
        EndPoints(route = "/api/auth/logInEmailVerificationCheck")

    data object SetDetails : EndPoints(route = "/api/auth/setDetails")
    data object GetDetails : EndPoints(route = "/api/auth/getDetails")

    data object UpdateDetails : EndPoints(route = "/api/auth/updateDetails")
    data object UpdateHeadDetails : EndPoints(route = "/api/auth/updateHeadDetails")
    data object UpdateAddress : EndPoints(route = "/api/auth/updateAddress")

    data object UpdateProfilePic : EndPoints(route = "/api/updateProfilePic")
    data object GetProfilePic : EndPoints(route = "/api/auth/getProfilePic")
    data object GetLeaveBalance : EndPoints(route = "/api/auth/getLeaveBalance")

    data object ApplyLeave : EndPoints(route = "/api/auth/applyLeave")
    data object GetHistoryLeaves : EndPoints(route = "/api/auth/getHistoryLeaves")

    data object GetApproveLeaves : EndPoints(route = "/api/auth/getApproveLeaves")
    data object HandleLeave : EndPoints(route = "/api/auth/handleLeave")

    data object GetViewLeaves : EndPoints(route = "/api/auth/viewLeaves")
}