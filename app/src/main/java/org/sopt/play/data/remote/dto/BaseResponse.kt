package org.sopt.play.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("isSuccess")
    val isSuccess: Boolean = false, // 요청 성공 여부
    @SerialName("code")
    val code: String? = null, // 서버가 정한 결과 코드 (ex. SUCCESS, POST_NOT_FOUND)
    @SerialName("message")
    val message: String? = null, // 결과 메시지 (실패하면 토스트로 보여줄 수 있음)
    @SerialName("data")
    val data: T? = null, // 실제 데이터 (실패하면 비어서 올 수 있어서 null 허용)
)
