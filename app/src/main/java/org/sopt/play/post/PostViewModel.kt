package org.sopt.play.post

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.play.data.Part
import org.sopt.play.data.local.AuthStorage
import org.sopt.play.data.remote.RetrofitClient
import org.sopt.play.data.remote.dto.AuthorizationCodeRequest
import org.sopt.play.data.remote.dto.CreatePostRequest
import org.sopt.play.data.remote.errorMessage

// 게시글 화면 SideEffect (한 번만 처리되는 이벤트)
sealed interface PostSideEffect {
    data class ShowToast(val message: String) : PostSideEffect
    // 토스트 → 띄울 메시지를 같이 전달 (API 실패했을 때 씀)
}

class PostViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PostUiState())
    // 수정 가능한 상태 (ViewModel 안에서만 바꿈)

    val uiState: StateFlow<PostUiState> = _uiState.asStateFlow()
    // Screen에는 읽기 전용으로 공개

    private val _sideEffect = MutableSharedFlow<PostSideEffect>()
    // 한 번만 보낼 이벤트용 (토스트)

    val sideEffect: SharedFlow<PostSideEffect> = _sideEffect.asSharedFlow()
    // Route에는 읽기 전용으로 공개 → 듣기만 함

    init {
        // ViewModel이 처음 만들어질 때 딱 한 번 실행 = 게시글 페이지 첫 진입
        getPosts()
    }

    fun onKeywordChange(keyword: String) {
        _uiState.update { it.copy(keyword = keyword) }
    }

    fun onFabClick() {
        _uiState.update {
            it.copy(
                isSheetVisible = true,
                title = "",
                content = "",
            )
        }
    }

    fun onSheetDismiss() {
        _uiState.update { it.copy(isSheetVisible = false) }
    }

    // 제목 20자까지만 받기
    fun onTitleChange(title: String) {
        if (title.length > MAX_TITLE_LENGTH) return
        _uiState.update { it.copy(title = title) }
    }

    // 본문 1000자까지만 받기
    fun onContentChange(content: String) {
        if (content.length > MAX_CONTENT_LENGTH) return
        _uiState.update { it.copy(content = content) }
    }

    // 완료 버튼 시트 닫고 게시글 생성 API 호출
    fun onCompleteClick(author: String) {
        val state = _uiState.value
        if (!state.isCompleteEnabled) return

        val request = CreatePostRequest(
            title = state.title,
            content = state.content,
            author = author,
        )

        _uiState.update { it.copy(isSheetVisible = false, title = "", content = "") }

        viewModelScope.launch {
            runCatching {
                val authorizationCode = getAuthorizationCode()
                RetrofitClient.apiService.createPost(
                    authorizationCode = authorizationCode,
                    request = request,
                )
            }.onSuccess { response ->
                if (response.isSuccessful) {
                    getPosts()
                } else {
                    _sideEffect.emit(
                        PostSideEffect.ShowToast(response.errorMessage("게시글을 작성하지 못했어요.")),
                    )
                }
            }.onFailure { e ->
                Log.e("PostViewModel", "게시글 생성 실패", e)
                _sideEffect.emit(PostSideEffect.ShowToast("게시글을 작성하지 못했어요."))
            }
        }
    }

    // 카드 길게 누름 → 이 글을 지울 대상으로 기억 → 모달 뜸
    fun onPostLongClick(postId: Long) {
        _uiState.update { it.copy(deleteTargetId = postId) }
    }

    // 모달 취소 / 바깥 터치 → 대상 비우기 → 모달 닫힘
    fun onDeleteDialogDismiss() {
        _uiState.update { it.copy(deleteTargetId = null) }
    }

    // 모달 삭제 버튼 → 모달 닫고 게시글 삭제 API 호출
    fun onDeleteConfirm() {
        val postId = _uiState.value.deleteTargetId ?: return
        // 지울 id 먼저 꺼내기 (null이면 지울 게 없으니 끝)

        _uiState.update { it.copy(deleteTargetId = null) }

        viewModelScope.launch {
            runCatching {
                val authorizationCode = getAuthorizationCode()
                RetrofitClient.apiService.deletePost(
                    authorizationCode = authorizationCode,
                    postId = postId,
                )
            }.onSuccess { response ->
                if (response.isSuccessful) {
                    _uiState.update { state ->
                        state.copy(posts = state.posts.filterNot { post -> post.id == postId })
                    }
                    // 서버에서 지웠으니 내 목록에서도 그 글만 빼기 (다시 조회 안 함)
                } else {
                    _sideEffect.emit(
                        PostSideEffect.ShowToast(response.errorMessage("게시글을 삭제하지 못했어요.")),
                    )
                }
            }.onFailure { e ->
                Log.e("PostViewModel", "게시글 삭제 실패", e)
                _sideEffect.emit(PostSideEffect.ShowToast("게시글을 삭제하지 못했어요."))
            }
        }
    }

    // 인증 코드 꺼내기
    private suspend fun getAuthorizationCode(): String {
        AuthStorage.authorizationCode?.let { savedCode -> return savedCode }

        val response = RetrofitClient.apiService.createAuthorizationCode(
            AuthorizationCodeRequest(part = Part.ANDROID.name),
        )
        val body = response.body()
        if (!response.isSuccessful || body == null) {
            throw IllegalStateException("인증 코드 발급 실패")
        }

        val newCode = body.data.authorizationCode
        AuthStorage.authorizationCode = newCode
        return newCode
    }

    // 게시글 목록 조회 API 호출
    private fun getPosts() {
        viewModelScope.launch {
            runCatching {
                RetrofitClient.apiService.getPosts()
            }.onSuccess { response ->
                val data = response.body()?.data
                if (response.isSuccessful && data != null) {
                    _uiState.update { state ->
                        state.copy(posts = data.posts.map { it.toPost() })
                    }
                } else {
                    _sideEffect.emit(
                        PostSideEffect.ShowToast(response.errorMessage("게시글을 불러오지 못했어요.")),
                    )
                }
            }.onFailure { e ->
                Log.e("PostViewModel", "게시글 목록 조회 실패", e)
                _sideEffect.emit(PostSideEffect.ShowToast("네트워크 오류가 발생했어요."))
            }
        }
    }

    companion object {
        private const val MAX_TITLE_LENGTH = 20 // 제목 최대 20자
        private const val MAX_CONTENT_LENGTH = 1000 // 본문 최대 1,000자
    }
}
