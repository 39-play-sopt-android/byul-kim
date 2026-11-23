package org.sopt.play

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

// SideEffect
sealed interface MainSideEffect {
    data class ShowToast(val message: String) : MainSideEffect
}

// ViewModel
class MainViewModel : ViewModel() {
    private val _sideEffect = MutableSharedFlow<MainSideEffect>()
    val sideEffect: SharedFlow<MainSideEffect> = _sideEffect.asSharedFlow()

    fun onPartClick(part: Part) = viewModelScope.launch {
        runCatching {
            RetrofitClient.apiService.createAuthorizationCode(
                request = AuthorizationCodeRequest(part.name),
            )
        }.onSuccess {response ->
            if (response.isSuccessful) {
                _sideEffect.emit(MainSideEffect.ShowToast("${part}로 토큰 발급 성공!"))
            } else {
                val message = response.body()?.message ?: "회원가입에 실패했습니다"
                _sideEffect.emit(MainSideEffect.ShowToast(message))
            }
        }.onFailure { e ->
            _sideEffect.emit(MainSideEffect.ShowToast("오류가 발생했습니다"))
            Log.e("Main", "회원가입 실패", e)
        }
    }
}

