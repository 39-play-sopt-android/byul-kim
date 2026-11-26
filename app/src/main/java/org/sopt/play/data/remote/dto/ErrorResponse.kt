package org.sopt.play.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerialName("code")
    val code: String? = null,
    @SerialName("message")
    val message: String? = null,
)
