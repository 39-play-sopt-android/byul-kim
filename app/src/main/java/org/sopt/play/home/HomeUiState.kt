package org.sopt.play.home

// 홈 화면 UI 상태
// HomeViewModel.kt 안에 있다가 Login, Register랑 맞춰서 따로 파일로 분리함
data class HomeUiState(
    val selectedMenu: HomeMenu = HomeMenu.HOME, // 선택된 메뉴 탭 (처음엔 홈)
    val selectedCategory: ProductCategory = ProductCategory.ALL, // 선택된 카테고리 칩 (처음엔 전체)
    val products: List<Product> = emptyList(), // 상품 목록 (처음엔 빈 리스트 → init에서 채움)
)
