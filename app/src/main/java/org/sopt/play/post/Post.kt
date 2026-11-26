package org.sopt.play.post

import org.sopt.play.data.remote.dto.PostResponse

// 화면(카드)에 보여줄 게시글 하나
data class Post(
    val id: Long,
    val title: String,
    val content: String,
    val author: String,
    val createdDate: String,
)

// 서버 응답 → 화면용 Post로 바꾸는 함수
fun PostResponse.toPost(): Post = Post(
    id = id,
    title = title,
    content = content,
    author = author,
    createdDate = createdAt.toDisplayDate(),
)

private fun String.toDisplayDate(): String = take(10).replace("-", ".")
