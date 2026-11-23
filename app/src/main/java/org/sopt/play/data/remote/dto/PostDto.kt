package org.sopt.play.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// 게시글 하나
// 목록 조회 응답의 posts 안에 하나씩 들어있음
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
@Serializable
data class PostSliceResponse(
    @SerialName("posts")
    val posts: List<PostResponse> = emptyList(), // 게시글 목록
    @SerialName("nextCursor")
    val nextCursor: Long? = null,
    @SerialName("hasNext")
    val hasNext: Boolean = false,
)
