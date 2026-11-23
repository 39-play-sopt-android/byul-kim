package org.sopt.play.data.remote

import org.sopt.play.data.remote.dto.AuthorizationCodeRequest
import org.sopt.play.data.remote.dto.AuthorizationCodeResponse
import org.sopt.play.data.remote.dto.BaseResponse
import org.sopt.play.data.remote.dto.PostSliceResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// 서버 API 목록
interface ApiService {
    @POST("/api/v1/members/authorization-code")
    suspend fun createAuthorizationCode(
        @Body request: AuthorizationCodeRequest,
    ): Response<AuthorizationCodeResponse>

    @GET("/api/v1/posts")
    suspend fun getPosts(): Response<BaseResponse<PostSliceResponse>>
}
