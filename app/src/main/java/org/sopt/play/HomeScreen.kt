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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.component.CategoryChip
import org.sopt.play.component.PlaySoptTopBar
import org.sopt.play.component.ProductCard
import org.sopt.play.component.TabMenuItem
import org.sopt.play.ui.theme.PlaySoptTheme

// 메뉴 탭 목록
// enum class = 정해진 값만 쓸 수 있는 목록
// HOME = 코드에서 부르는 이름 / label("홈") = 화면에 보여줄 글자
private enum class HomeMenu(val label: String) {
    DOUBLE_POINT("2배적립"), HOME("홈"), SELECTED("셀렉티드"), BRAND("브랜드"),
    BEAUTY("뷰티"), LIFE("라이프"), RANKING("랭킹"),
}

// 배너 이미지 목록
private val bannerImages = listOf(
    R.drawable.img_banner1,
    R.drawable.img_banner2,
    R.drawable.img_banner3,
)

// 카테고리 칩 목록
private enum class ProductCategory(val label: String) {
    ALL("전체"), TOP("상의"), PANTS("팬츠"), ONEPIECE("원피스"), OUTER("아우터"),
    SKIRT("스커트"), SHOES("슈즈"), BAG("가방"), INNERWEAR("이너웨어/잠옷"), PHONE_DECO("폰꾸미기"),
}

// 홈 화면 (로그인 성공 후 도착하는 화면)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    // 지금 선택된 메뉴 (처음엔 HOME)
    // enum은 저장 가능한 타입이라 rememberSaveable에 그대로 넣어도 됨 → 화면 돌려도 선택 유지
    var selectedMenu by rememberSaveable { mutableStateOf(HomeMenu.HOME) }

    // 지금 선택된 카테고리 (처음엔 ALL = 전체)
    var selectedCategory by rememberSaveable { mutableStateOf(ProductCategory.ALL) }

    // Scaffold = 화면 뼈대
    Scaffold(
        modifier = modifier,
        containerColor = PlaySoptTheme.colors.white,       // 화면 배경 흰색
        topBar = { PlaySoptTopBar() },
        // topBar 자리에 넣은 건 스크롤해도 안 움직이고 위에 고정됨
        // 피그마 세로 스크롤 화면을 보면 PLAY SOPT만 고정이라서 여기에 넣음
    ) { innerPadding ->
        // innerPadding = Scaffold가 계산해서 주는 여백
        // 여기서는 탑바 높이만큼 → 이만큼 안 띄우면 내용이 탑바 밑에 깔려서 가려짐

        // LazyVerticalGrid = 세로로 스크롤되는 격자(그리드)
        // Lazy = 화면에 보이는 것만 그려서 효율적 (상품이 100개여도 보이는 몇 개만 그림)
        // 상품 카드가 2칸씩 격자라서 Grid를 씀
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()            // 남은 화면 꽉 채우기
                .padding(innerPadding),   // 탑바 높이만큼 위를 띄우기

            columns = GridCells.Fixed(2),
            // 한 줄에 몇 칸? → Fixed(2) = 무조건 2칸
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) { // 이 안에서는 item { }, items { }로 항목을 등록함

            // 한 줄을 통째로 쓰는 항목
            item(span = { GridItemSpan(maxLineSpan) }) {
                // span = 이 항목이 몇 칸을 차지할지
                // GridItemSpan(maxLineSpan) = 한 줄의 최대 칸 수만큼 = 2칸 전부 = 가로 꽉 차게
                // 메뉴 탭 (아래에 따로 만든 HomeMenuTabs 함수를 불러옴)
                HomeMenuTabs(
                    selectedMenu = selectedMenu,           // 지금 선택된 메뉴를 알려줌
                    onMenuClick = { selectedMenu = it },   // 눌리면 선택된 메뉴를 바꿈 (it = 눌린 메뉴)
                )
            }

            // 배너 (가로 스크롤)
            item(span = { GridItemSpan(maxLineSpan) }) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(bannerImages) { image ->
                        // bannerImages에서 그림 주소를 하나씩 꺼내서 image에 담음
                        Image(
                            painter = painterResource(image),    // 그 주소의 그림 불러오기
                            contentDescription = null,           // 꾸밈용 사진이라 설명 없음
                            contentScale = ContentScale.Crop,    // 칸에 꽉 채우고 넘치는 부분은 자르기
                            modifier = Modifier
                                .width(328.dp)
                                .aspectRatio(328f / 195f),
                        )
                    }
                }
            }

            // 배너 ↔ 제목 간격 20 (아이템 사이 간격은 Spacer가 담당)
            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier.height(20.dp))
            }

            // 제목
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),  // 좌우 여백만 (화면 가장자리와의 거리)
                    text = "김솝트님을 위한 추천 아이템",
                    style = PlaySoptTheme.typography.m18,
                )
            }

            // 제목 ↔ 칩 간격 12
            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier.height(12.dp))
            }

            // 카테고리 칩 (아래에 따로 만든 CategoryChips 함수를 불러옴)
            item(span = { GridItemSpan(maxLineSpan) }) {
                CategoryChips(
                    selectedCategory = selectedCategory,
                    onCategoryClick = { selectedCategory = it },
                )
            }

            // 칩 ↔ 상품 간격 20
            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier.height(20.dp))
            }

            // 상품 카드 (2개씩 나란히)
            items(productList) { product ->
                // productList에서 상품을 하나씩 꺼내서 product에 담음
                ProductCard(product = product)
            }
        }

    }
}

// 메뉴 탭 한 줄 (홈 화면과 Preview에서 같이 씀)
// 선택 상태는 HomeScreen이 갖고 있고, 여기엔 "선택된 값"과 "누르면 할 일"만 받아옴
@Composable
private fun HomeMenuTabs(
    selectedMenu: HomeMenu,              // 지금 선택된 메뉴 (밖에서 알려줌)
    onMenuClick: (HomeMenu) -> Unit,     // 메뉴 눌렀을 때 할 일 (밖에서 정함)
    modifier: Modifier = Modifier,
) {
    // 탭 개수가 7개로 고정이라 Lazy 대신 Row + 가로 스크롤
    // rememberScrollState() = 어디까지 스크롤했는지 기억
    Row(modifier = modifier.horizontalScroll(rememberScrollState())) {
        HomeMenu.entries.forEach { menu ->
            // HomeMenu.entries = enum 값 전체 (2배적립, 홈, 셀렉티드, ...) 를 하나씩 꺼냄
            TabMenuItem(
                text = menu.label,                   // 화면엔 "홈" 같은 글자
                isSelected = menu == selectedMenu,   // 나 == 선택된 메뉴면 선택!
                onClick = { onMenuClick(menu) },     // 눌린 메뉴를 밖(HomeScreen)으로 알려줌
            )
        }
    }
}

// 카테고리 칩 한 줄 (칩은 10개라 LazyRow 유지)
@Composable
private fun CategoryChips(
    selectedCategory: ProductCategory,            // 지금 선택된 카테고리 (밖에서 알려줌)
    onCategoryClick: (ProductCategory) -> Unit,   // 칩 눌렀을 때 할 일 (밖에서 정함)
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        items(ProductCategory.entries) { category ->
            CategoryChip(
                text = category.label,                       // 화면엔 "전체" 같은 글자
                isSelected = category == selectedCategory,   // 나 == 선택된 카테고리면 선택!
                onClick = { onCategoryClick(category) },     // 눌린 칩을 밖으로 알려줌
            )
        }
    }
}

// 이 파일의 주인공(HomeScreen) 전체 미리보기
@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    PlaySoptTheme {
        HomeScreen()
    }
}