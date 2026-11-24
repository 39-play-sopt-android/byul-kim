package org.sopt.play.data.remote

import kotlinx.serialization.json.Json
import org.sopt.play.data.remote.dto.ErrorResponse
import retrofit2.Response

// 실패 응답 JSON을 읽을 때 쓸 설정
private val errorJson = Json { ignoreUnknownKeys = true }

// 실패 응답에서 서버가 보낸 message 꺼내기
// Response<*> = 어떤 타입의 Response든 상관없이 쓸 수 있게
fun Response<*>.errorMessage(defaultMessage: String): String {
    val raw = errorBody()?.string() ?: return defaultMessage
    return runCatching { errorJson.decodeFromString<ErrorResponse>(raw).message }
        .getOrNull()
        ?: defaultMessage
}
