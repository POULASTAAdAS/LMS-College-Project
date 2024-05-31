package com.poulastaa.domain.repository

import com.poulastaa.data.model.EndPoints
import com.poulastaa.data.model.GetTeacherRes
import com.poulastaa.data.model.auth.req.SetDetailsReq
import com.poulastaa.data.model.auth.res.*
import com.poulastaa.data.model.details.UpdateAddressReq
import com.poulastaa.data.model.details.UpdateDetailsReq
import com.poulastaa.data.model.leave.ApplyLeaveReq
import com.poulastaa.data.model.leave.ApplyLeaveRes
import com.poulastaa.data.model.leave.ApplyLeaveStatus
import com.poulastaa.data.model.leave.GetBalanceRes
import com.poulastaa.data.model.table.department.DepartmentHeadTable
import com.poulastaa.data.model.table.department.DepartmentTable
import com.poulastaa.data.model.table.utils.PathTable
import com.poulastaa.data.repository.JWTRepository
import com.poulastaa.data.repository.TeacherRepository
import com.poulastaa.data.repository.ServiceRepository
import com.poulastaa.data.repository.leave.LeaveWrapper
import com.poulastaa.domain.dao.department.Department
import com.poulastaa.domain.dao.department.DepartmentHead
import com.poulastaa.domain.dao.utils.HeadClark
import com.poulastaa.domain.dao.utils.Path
import com.poulastaa.domain.dao.utils.Principal
import com.poulastaa.invalidTokenList
import com.poulastaa.plugins.dbQuery
import com.poulastaa.utils.Constants.LOGIN_VERIFICATION_MAIL_TOKEN_CLAIM_KEY
import com.poulastaa.utils.Constants.SIGNUP_VERIFICATION_MAIL_TOKEN_CLAIM_KEY
import com.poulastaa.utils.sendEmail
import kotlinx.coroutines.*
import java.io.File
import java.time.LocalDateTime

class ServiceRepositoryImpl(
    private val jwtRepo: JWTRepository,
    private val teacher: TeacherRepository,
    private val leave: LeaveWrapper
) : ServiceRepository {
    override suspend fun auth(email: String): AuthRes {
        if (!validateEmail(email)) return AuthRes()

        val response = teacher.getTeacherDetailsStatus(email)

        return when (response.first) {
            AuthStatus.PRINCIPLE_FOUND -> {
                val token = jwtRepo.generateLogInVerificationMailToken(email = email)
                sendEmailVerificationMail(email, token, EndPoints.VerifyLogInEmail.route)

                val principle = response.second as Principal

                AuthRes(
                    authStatus = response.first,
                    user = User(
                        name = principle.name,
                        email = principle.email,
                        profilePicUrl = principle.profilePic
                    )
                )
            }

            AuthStatus.SIGNUP -> {
                val token = jwtRepo.generateSignUpVerificationMailToken(email = email)
                sendEmailVerificationMail(email, token, EndPoints.VerifySignUpEmail.route)


                AuthRes(
                    authStatus = response.first
                )
            }

            AuthStatus.LOGIN -> {
                val token = jwtRepo.generateLogInVerificationMailToken(email = email)
                sendEmailVerificationMail(email, token, EndPoints.VerifyLogInEmail.route)

                val user = teacher.getTeacherWithDetails(email)

                AuthRes(
                    authStatus = response.first,
                    user = user
                )
            }

            else -> AuthRes()
        }
    }

    override suspend fun updateSignUpVerificationStatus(token: String): VerifiedMailStatus {
        val result = jwtRepo.verifyJWTToken(token, SIGNUP_VERIFICATION_MAIL_TOKEN_CLAIM_KEY)
            ?: return VerifiedMailStatus.TOKEN_NOT_VALID

        if (result == VerifiedMailStatus.TOKEN_USED.name) return VerifiedMailStatus.TOKEN_USED

        invalidTokenList.add(token)

        val response = teacher.updateSignUpVerificationStatus(result)

        return response
    }

    override suspend fun updateLogInVerificationStatus(token: String): Pair<VerifiedMailStatus, Pair<String, String>> {
        val result = jwtRepo.verifyJWTToken(token, LOGIN_VERIFICATION_MAIL_TOKEN_CLAIM_KEY)
            ?: return VerifiedMailStatus.TOKEN_NOT_VALID to Pair("", "")

        if (result == VerifiedMailStatus.TOKEN_USED.name) return VerifiedMailStatus.TOKEN_USED to Pair("", "")

        invalidTokenList.add(token)

        val response = teacher.updateLogInVerificationStatus(result)

        return response
    }

    override suspend fun signUpEmailVerificationCheck(email: String): EmailVerificationRes {
        val status = teacher.signupEmailVerificationCheck(email)

        return if (status) EmailVerificationRes(status = true)
        else EmailVerificationRes()
    }

    override suspend fun loginEmailVerificationCheck(email: String): Pair<EmailVerificationRes, String?> {
        val status = teacher.loginEmailVerificationCheck(email)

        return if (status) {
            val name = teacher.getTeacherWithDetails(email).name

            EmailVerificationRes(status = true) to name
        } else EmailVerificationRes() to null
    }

    override suspend fun saveTeacherDetails(req: SetDetailsReq): SetDetailsRes {
        if (!req.validateDetails()) return SetDetailsRes()

        return teacher.saveTeacherDetails(req)
    }

    override suspend fun getTeacherDetails(email: String): GetTeacherRes? = teacher.getTeacherDetailsRes(email)

    override suspend fun updateDetails(
        email: String,
        req: UpdateDetailsReq
    ): Boolean {
        if (req.email.isNotEmpty() && !validateEmail(req.email)) return false

        return teacher.updateDetails(email, req)
    }

    override suspend fun updateAddress(email: String, req: UpdateAddressReq): Boolean =
        teacher.updateAddress(email, req)

    override suspend fun storeProfilePic(
        email: String,
        name: String,
    ): Boolean = teacher.storeProfilePic(
        email = email,
        fileNameWithPath = name,
    )


    override suspend fun getProfilePic(email: String): File? = try {
        teacher.getProfilePic(email)?.let { File("${System.getenv("profileFolder")}$it") }
    } catch (e: Exception) {
        null
    }

    private fun sendEmailVerificationMail(
        toEmail: String,
        token: String,
        route: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            sendEmail( // verification mail
                to = toEmail,
                subject = "Authentication Mail",
                content = (
                        (
                                "<html>"
                                        + "<body>"
                                        + "<h1>Email Authentication</h1>"
                                        + "<p>Click the following link to verify your email:</p>"
                                        + "<a href=\"${System.getenv("BASE_URL") + route}?token=" + token
                                ) + "\">Authenticate</a>"
                                + "</body>"
                                + "</html>"
                        )
            )
        }
    }


    override suspend fun getLeaveBalance(type: String, email: String): GetBalanceRes {
        val teacher = teacher.getTeacher(email) ?: return GetBalanceRes()

        return leave.leaveUtils.getLeaveBalance(
            teacherId = teacher.id.value,
            type = type
        )?.let {
            GetBalanceRes(
                balance = it
            )
        } ?: GetBalanceRes()
    }

    override suspend fun handleLeaveReq(
        req: ApplyLeaveReq,
        filePath: String?
    ): ApplyLeaveRes {
        val response = leave.applyLeave.applyLeave(
            req = req,
            doc = filePath
        )

        // send mails
        if (response.status == ApplyLeaveStatus.ACCEPTED) CoroutineScope(Dispatchers.IO).launch {
            val sendMailToTeacher = async {
                leaveAcceptanceLetter(
                    to = req.email,
                    leaveType = req.leaveType,
                    fromDate = req.fromDate,
                    toDate = req.toDate,
                    totalDays = req.totalDays,
                    reqDateTime = LocalDateTime.now().toString()
                )
            }

            val sendMailToHead = async {
                val pathDef = async {
                    dbQuery {
                        Path.find {
                            PathTable.type eq req.path
                        }.single()
                    }
                }

                val teacherDetails = dbQuery {
                    teacher.getTeacher(req.email)?.let {
                        teacher.getTeacherDetails(it.email, it.id.value)
                    }
                } ?: return@async

                val departmentHead = dbQuery {
                    DepartmentHead.find {
                        DepartmentHeadTable.departmentId eq teacherDetails.departmentId
                    }.single()
                }

                val department = dbQuery {
                    Department.find {
                        DepartmentTable.id eq departmentHead.departmentId
                    }.single()
                }

                val email = when (Path.PathType.valueOf(pathDef.await().type)) {
                    Path.PathType.PRINCIPLE -> dbQuery { Principal.all().first().email }

                    Path.PathType.HEAD_CLARK -> dbQuery { HeadClark.all().first().email }

                    Path.PathType.DEPARTMENT_HEAD -> dbQuery { teacher.getTeacherOnId(departmentHead.teacherId.value).email }
                }

                leaveReqNotificationToHead(
                    to = email,
                    leaveType = req.leaveType,
                    fromDate = req.fromDate,
                    toDate = req.toDate,
                    totalDays = req.totalDays,
                    reqDateTime = LocalDateTime.now().toString(),
                    department = department.name,
                    name = teacherDetails.name
                )
            }

            sendMailToTeacher.await()
            sendMailToHead.await()
        }


        return response
    }

    private fun validateEmail(email: String) =
        email.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$"))

    private fun SetDetailsReq.validateDetails(): Boolean {
        if (this.email.isBlank() ||
            this.name.isBlank() ||
            this.dbo.isBlank() ||
            this.exp.isBlank()
        ) return false

        if (this.sex.uppercaseChar() !in listOf('M', 'F', 'O')) return false

        if (phone_1.length != 10) return false

        if (phone_2.isNotBlank()) if (phone_2.length != 10) return false

        if (department.isBlank() ||
            hrmsId.isBlank() ||
            designation.isBlank() ||
            joiningDate.isBlank()
        ) return false

        val areAddressesValid = this.address.all {
            it.second.houseNumber.isNotBlank() &&
                    it.second.street.isNotBlank() &&
                    it.second.city.isNotBlank() &&
                    it.second.zipcode.isNotBlank() &&
                    it.second.state.isNotBlank() &&
                    it.second.country.isNotBlank()
        }

        return areAddressesValid
    }


    private fun leaveAcceptanceLetter(
        to: String,
        leaveType: String,
        fromDate: String,
        toDate: String,
        totalDays: String,
        reqDateTime: String
    ) {
        val subject = "Leave Request Accepted"
        val messageContent = """
            Your leave request for $leaveType from $fromDate to $toDate,A total days of $totalDays days, has been accepted.
            Request submitted on: $reqDateTime.
            
            This is an auto-generated mail. Please do not reply to this message.
            
            Regards,
            ${System.getenv("college")}
        """.trimIndent()

        sendEmail(
            to = to,
            subject = subject,
            content = messageContent
        )
    }

    private fun leaveReqNotificationToHead(
        to: String,
        leaveType: String,
        fromDate: String,
        toDate: String,
        totalDays: String,
        reqDateTime: String,
        department: String,
        name: String
    ) {
        val subject = "A Leave Request is made by $name"
        val messageContent = """
            A leave request is made by $name from Department $department of $leaveType from $fromDate to $toDate,A total days of $totalDays days.
            Request submitted on: $reqDateTime.
            
            This is an auto-generated mail. Please do not reply to this message.
            
            Regards,
            ${System.getenv("college")}
        """.trimIndent()

        sendEmail(
            to = to,
            subject = subject,
            content = messageContent
        )

    }
}