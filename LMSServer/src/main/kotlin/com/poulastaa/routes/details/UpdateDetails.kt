package com.poulastaa.routes.details

import com.poulastaa.data.model.EndPoints
import com.poulastaa.data.model.auth.UserSession
import com.poulastaa.data.model.details.UpdateAddressReq
import com.poulastaa.data.model.details.UpdateDetailsReq
import com.poulastaa.data.model.details.UpdateHeadDetailsReq
import com.poulastaa.data.repository.ServiceRepository
import com.poulastaa.utils.Constants.SESSION_AUTH
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Route.updateDetails(service: ServiceRepository) {
    authenticate("session-auth") {
        route(EndPoints.UpdateDetails.route) {
            post {
                val payload = call.sessions.get<UserSession>()
                    ?: return@post call.respondRedirect(EndPoints.UnAuthorised.route)

                val req = call.receiveNullable<UpdateDetailsReq>()
                    ?: return@post call.respondRedirect(EndPoints.UnAuthorised.route)


                val response = service.updateDetails(payload.email, req)

                call.respond(
                    message = response,
                    status = HttpStatusCode.OK
                )
            }
        }
    }
}

fun Route.updateHeadDetails(service: ServiceRepository) {
    authenticate(SESSION_AUTH) {
        route(EndPoints.UpdateHeadDetails.route) {
            post {
                val payload = call.sessions.get<UserSession>()
                    ?: return@post call.respondRedirect(EndPoints.UnAuthorised.route)

                val req = call.receiveNullable<UpdateHeadDetailsReq>()
                    ?: return@post call.respondRedirect(EndPoints.UnAuthorised.route)

                val response = service.updateHeadDetails(payload.email, req)

                call.respond(
                    message = response,
                    status = HttpStatusCode.OK
                )
            }
        }
    }
}

fun Route.updateAddress(service: ServiceRepository) {
    authenticate("session-auth") {
        route(EndPoints.UpdateAddress.route) {
            post {
                val payload = call.sessions.get<UserSession>()
                    ?: return@post call.respondRedirect(EndPoints.UnAuthorised.route)

                val req = call.receiveNullable<UpdateAddressReq>()
                    ?: return@post call.respondRedirect(EndPoints.UnAuthorised.route)

                val response = service.updateAddress(
                    email = payload.email,
                    req = req
                )

                call.respond(
                    message = response,
                    status = HttpStatusCode.OK
                )
            }
        }
    }
}