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
import org.sopt.play.data.remote.RetrofitClient

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
        // 화면 회전해도 ViewModel은 유지되니까 다시 호출 안 됨
        getPosts()
    }

    // 검색창 글자 바뀜
    fun onKeywordChange(keyword: String) {
        _uiState.update { it.copy(keyword = keyword) }
    }

    // 게시글 목록 조회 API 호출
    private fun getPosts() {
        viewModelScope.launch {
            // viewModelScope = ViewModel이 사라지면 같이 취소됨 → 직접 안 꺼도 됨
            runCatching {
                RetrofitClient.apiService.getPosts()
            }.onSuccess { response ->
                val data = response.body()?.data
                if (response.isSuccessful && data != null) {
                    _uiState.update { state ->
                        state.copy(posts = data.posts.map { it.toPost() })
                        // 서버 게시글(PostResponse) 하나하나를 화면용 Post로 바꿔서 상태에 넣음
                    }
                } else {
                    _sideEffect.emit(
                        PostSideEffect.ShowToast(
                            response.body()?.message ?: "게시글을 불러오지 못했어요.",
                        ),
                    )
                }
            }.onFailure { e ->
                Log.e("PostViewModel", "게시글 목록 조회 실패", e) // Logcat에 에러 기록
                _sideEffect.emit(PostSideEffect.ShowToast("네트워크 오류가 발생했어요."))
            }
        }
    }
}
