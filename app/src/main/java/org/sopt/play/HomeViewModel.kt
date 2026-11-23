package org.sopt.play

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// HomeUiState(화면 상태)는 HomeUiState.kt로 분리함
// → 이 파일에는 로직(ViewModel)만 남김

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    // 수정 가능한 상태 (ViewModel 내부에서만 변경)

    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    // Screen에는 읽기 전용으로 공개

    // 홈은 토스트/화면 이동이 없어서 SideEffect를 만들지 않음

    init {
        // ViewModel 인스턴스가 처음 만들어질 때 딱 한 번 실행되는 블록
        // 화면 회전해도 ViewModel은 유지되니까 다시 실행 안 됨
        loadProducts()
    }

    private fun loadProducts() {
        _uiState.update { it.copy(products = dummyProductList) }
        // Product.kt의 더미 상품 목록을 상태에 넣음
        // 나중에 상품 API가 생기면 이 함수 안만 API 호출로 바꾸면 됨
        // private → ViewModel 안에서만 호출 (Screen이 부를 일 없음)
    }

    fun onMenuClick(menu: HomeMenu) {
        _uiState.update { it.copy(selectedMenu = menu) }
        // 현재 상태 복사 + selectedMenu만 클릭된 메뉴로 교체
    }

    fun onCategoryClick(category: ProductCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
        // 현재 상태 복사 + selectedCategory만 클릭된 카테고리로 교체
    }

    // 하트 클릭 -> 해당 상품의 좋아요 상태 반전
    fun onFavoriteClick(productId: Int) {
        _uiState.update { state ->
            // state = 현재 HomeUiState (it 대신 이름 붙임 → 안에서 product랑 헷갈리지 않게)
            state.copy(
                products = state.products.map { product ->
                    // map = 리스트를 하나씩 돌면서 변환한 새 리스트를 만듦 (원본 리스트는 안 바뀜)
                    if (product.id == productId) {
                        product.copy(isFavorite = !product.isFavorite)
                        // 클릭된 상품 → isFavorite만 반대로 바꾼 새 Product
                    } else {
                        product
                        // 나머지 상품 -> 그대로
                    }
                },
            )
        }
    }
}
