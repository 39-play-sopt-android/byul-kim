package org.sopt.play.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// [POST] /api/v1/posts 요청 바디 (게시글 생성)
@Serializable
data class CreatePostRequest(
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String,
    @SerialName("author")
    val author: String,
)

// 게시글 하나
// 목록 조회 응답의 posts 안에 하나씩 들어있음 (생성 응답의 data도 같은 모양)
@Serializable
data class PostResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String,
    @SerialName("author")
    val author: String,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("updatedAt")
    val updatedAt: String? = null,
)

// [GET] /api/v1/posts 응답의 data 부분
// Slice = 전체 중 한 조각 (서버가 게시글을 한 번에 다 안 주고 나눠서 줌)
@Serializable
data class PostSliceResponse(
    @SerialName("posts")
    val posts: List<PostResponse> = emptyList(), // 게시글 목록
    @SerialName("nextCursor")
    val nextCursor: Long? = null,
    @SerialName("hasNext")
    val hasNext: Boolean = false,
)
