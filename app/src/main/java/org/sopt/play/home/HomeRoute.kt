package org.sopt.play.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

// 홈 Route = ViewModel이랑 HomeScreen을 이어주는 "연결 담당"
// 홈은 토스트/화면 이동(SideEffect)이 없어서 상태 구독 + 함수 연결만 함
@Composable
fun HomeRoute(
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
    // ViewModelProvider를 통해 HomeViewModel 받아오기 (직접 생성 X)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    // uiState 구독 → 값 바뀌면 리컴포지션 (백그라운드면 구독 멈춤)

    HomeScreen(
        uiState = uiState, // 화면에 그릴 상태
        onMenuClick = viewModel::onMenuClick, // 메뉴 탭 누르면 ViewModel 함수 호출
        onCategoryClick = viewModel::onCategoryClick, // 카테고리 칩 누르면 ViewModel 함수 호출
        onFavoriteClick = viewModel::onFavoriteClick, // 하트 누르면 ViewModel 함수 호출
        onSearchClick = onSearchClick,
        modifier = modifier,
    )
}
