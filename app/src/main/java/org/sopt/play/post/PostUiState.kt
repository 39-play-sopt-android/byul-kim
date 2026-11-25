package org.sopt.play.post

data class PostUiState(
    val posts: List<Post> = emptyList(),
    val keyword: String = "",

    val isSheetVisible: Boolean = false,
    val title: String = "",
    val content: String = "",
) {
    val isCompleteEnabled: Boolean
        get() = title.isNotBlank() && content.isNotBlank()
}
