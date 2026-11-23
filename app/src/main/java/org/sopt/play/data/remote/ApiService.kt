package org.sopt.play.data.remote

import org.sopt.play.data.remote.dto.AuthorizationCodeRequest
import org.sopt.play.data.remote.dto.AuthorizationCodeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/v1/members/authorization-code")
    suspend fun createAuthorizationCode(
        @Body request: AuthorizationCodeRequest,
    ): Response<AuthorizationCodeResponse>
}
