package org.sopt.play

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.sopt.play.component.CategoryChip
import org.sopt.play.component.PlaySoptTopBar
import org.sopt.play.component.ProductCard
import org.sopt.play.component.TabMenuItem
import org.sopt.play.ui.theme.PlaySoptTheme

// 메뉴 탭 목록
// enum class = 정해진 값만 쓸 수 있는 목록
// HOME = 코드에서 부르는 이름 / label("홈") = 화면에 보여줄 글자
// private 제거 → HomeViewModel.kt의 HomeUiState에서도 써야 해서
enum class HomeMenu(val label: String) {
    DOUBLE_POINT("2배적립"), HOME("홈"), SELECTED("셀렉티드"), BRAND("브랜드"),
    BEAUTY("뷰티"), LIFE("라이프"), RANKING("랭킹"),
}

// 배너 이미지 목록 (바뀌지 않는 고정 리소스라 상태 아님 → 여기 그대로 둠)
private val bannerImages = listOf(
    R.drawable.img_banner1,
    R.drawable.img_banner2,
    R.drawable.img_banner3,
)

// 카테고리 칩 목록
// private 제거 → HomeUiState에서도 써야 해서
enum class ProductCategory(val label: String) {
    ALL("전체"), TOP("상의"), PANTS("팬츠"), ONEPIECE("원피스"), OUTER("아우터"),
    SKIRT("스커트"), SHOES("슈즈"), BAG("가방"), INNERWEAR("이너웨어/잠옷"), PHONE_DECO("폰꾸미기"),
}

// 홈 화면 (로그인 성공 후 도착하는 화면)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
    // ViewModelProvider를 통해 HomeViewModel 받아오기 (직접 생성 X)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    // [uiState 구독 → 값 바뀌면 리컴포지션

    // Scaffold = 화면 뼈대
    Scaffold(
        modifier = modifier,
        containerColor = PlaySoptTheme.colors.white, // 화면 배경 흰색
        topBar = { PlaySoptTopBar() },
        // topBar 자리에 넣은 건 스크롤해도 안 움직이고 위에 고정됨
        // 피그마 세로 스크롤 화면을 보면 PLAY SOPT만 고정이라서 여기에 넣음
    ) { innerPadding ->
        // innerPadding = Scaffold가 계산해서 주는 여백 (탑바 높이만큼)

        // LazyVerticalGrid = 세로로 스크롤되는 격자(그리드)
        // Lazy = 화면에 보이는 것만 그려서 효율적
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize() // 남은 화면 꽉 채우기
                .padding(innerPadding), // 탑바 높이만큼 위를 띄우기
            columns = GridCells.Fixed(2), // 한 줄에 무조건 2칸
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            // 메뉴 탭 (한 줄 통째로)
            item(span = { GridItemSpan(maxLineSpan) }) {
                HomeMenuTabs(
                    selectedMenu = uiState.selectedMenu,
                    // 선택된 메뉴를 uiState에서 가져옴
                    onMenuClick = viewModel::onMenuClick,
                    // 메뉴 클릭되면 ViewModel 함수 호출 (2주차: { selectedMenu = it })
                )
            }

            // 배너 (가로 스크롤)
            item(span = { GridItemSpan(maxLineSpan) }) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(bannerImages) { image ->
                        Image(
                            painter = painterResource(image), // 그 주소의 그림 불러오기
                            contentDescription = null, // 꾸밈용 사진이라 설명 없음
                            contentScale = ContentScale.Crop, // 칸에 꽉 채우고 넘치는 부분은 자르기
                            modifier = Modifier
                                .width(328.dp)
                                .aspectRatio(328f / 195f),
                        )
                    }
                }
            }

            // 배너 ↔ 제목 간격 20
            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier.height(20.dp))
            }

            // 제목
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "김솝트님을 위한 추천 아이템",
                    style = PlaySoptTheme.typography.m18,
                )
            }

            // 제목 ↔ 칩 간격 12
            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier.height(12.dp))
            }

            // 카테고리 칩
            item(span = { GridItemSpan(maxLineSpan) }) {
                CategoryChips(
                    selectedCategory = uiState.selectedCategory,
                    // 선택된 카테고리를 uiState에서 가져옴
                    onCategoryClick = viewModel::onCategoryClick,
                    // 칩 클릭되면 ViewModel 함수 호출 (2주차: { selectedCategory = it })
                )
            }

            // 칩 ↔ 상품 간격 20
            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier.height(20.dp))
            }

            // 상품 카드 (2개씩 나란히)
            items(uiState.products) { product ->
                // 전역 productList 대신 uiState의 products 사용
                ProductCard(product = product)
            }
        }
    }
}

// 메뉴 탭 한 줄
// 선택 상태는 받아오기만 하고, 클릭은 밖으로 알림 (상태 끌어올리기)
@Composable
private fun HomeMenuTabs(
    selectedMenu: HomeMenu, // 지금 선택된 메뉴 (밖에서 알려줌)
    onMenuClick: (HomeMenu) -> Unit, // 메뉴 눌렀을 때 할 일 (밖에서 정함)
    modifier: Modifier = Modifier,
) {
    // 탭 개수가 7개로 고정이라 Lazy 대신 Row + 가로 스크롤
    Row(modifier = modifier.horizontalScroll(rememberScrollState())) {
        HomeMenu.entries.forEach { menu ->
            // HomeMenu.entries = enum 값 전체를 하나씩 꺼냄
            TabMenuItem(
                text = menu.label, // 화면엔 "홈" 같은 글자
                isSelected = menu == selectedMenu, // 나 == 선택된 메뉴면 선택!
                onClick = { onMenuClick(menu) }, // 눌린 메뉴를 밖으로 알려줌
            )
        }
    }
}

// 카테고리 칩 한 줄 (칩은 10개라 LazyRow 유지)
@Composable
private fun CategoryChips(
    selectedCategory: ProductCategory, // 지금 선택된 카테고리 (밖에서 알려줌)
    onCategoryClick: (ProductCategory) -> Unit, // 칩 눌렀을 때 할 일 (밖에서 정함)
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        items(ProductCategory.entries) { category ->
            CategoryChip(
                text = category.label, // 화면엔 "전체" 같은 글자
                isSelected = category == selectedCategory, // 나 == 선택된 카테고리면 선택!
                onClick = { onCategoryClick(category) }, // 눌린 칩을 밖으로 알려줌
            )
        }
    }
}

// HomeScreen 전체 미리보기
@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    PlaySoptTheme {
        HomeScreen()
    }
}