package org.sopt.play.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorizationCodeRequest(
    @SerialName("part")
    val part: String,
)

@Serializable
data class AuthorizationCodeResponse(
    @SerialName("isSuccess")
    val isSuccess: Boolean,
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: AuthorizationCode,
)

@Serializable
data class AuthorizationCode(
    @SerialName("authorizationCode")
    val authorizationCode: String,
)
