package org.sopt.play.post

// 게시글 화면에 보이는 값을 한 상자에 묶은 것
data class PostUiState(
    val posts: List<Post> = emptyList(),
    val keyword: String = "",
)
