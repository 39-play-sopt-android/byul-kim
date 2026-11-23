package org.sopt.play

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/v1/members/authorization-code")
    suspend fun createAuthorizationCode(
        @Body request: AuthorizationCodeRequest,
    ): Response<AuthorizationCodeResponse>
}