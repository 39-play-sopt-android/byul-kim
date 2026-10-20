package org.sopt.play

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import org.sopt.play.ui.theme.Black
import org.sopt.play.ui.theme.M18
import org.sopt.play.ui.theme.PlaySoptTheme
import org.sopt.play.ui.theme.White

// 메뉴 탭 목록
private val homeMenus = listOf("2배적립", "홈", "셀렉티드", "브랜드", "뷰티", "라이프", "랭킹")

// 배너 이미지 목록
private val bannerImages = listOf(
    R.drawable.img_banner1,
    R.drawable.img_banner2,
    R.drawable.img_banner3,
)

// 카테고리 칩 목록
private val categories = listOf("전체", "상의", "팬츠", "원피스", "아우터", "스커트", "슈즈", "가방", "이너웨어/잠옷", "폰꾸미기")

// 홈 화면 (로그인 성공 후 도착하는 화면)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    // 지금 선택된 메뉴 이름 (처음엔 "홈")
    var selectedMenu by rememberSaveable { mutableStateOf("홈") }

    // 지금 선택된 카테고리 이름 (처음엔 "전체")
    var selectedCategory by rememberSaveable { mutableStateOf("전체") }

    // Scaffold = 화면 뼈대
    Scaffold(
        modifier = modifier,
        containerColor = White,       // 화면 배경 흰색
        topBar = { PlaySoptTopBar() },
        // topBar 자리에 넣은 건 스크롤해도 안 움직이고 위에 고정됨
        // 피그마 세로 스크롤 화면을 보면 PLAY SOPT만 고정이라서 여기에 넣음
    ){innerPadding ->
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
        ){ // 이 안에서는 item { }, items { }로 항목을 등록함

            // 한 줄을 통째로 쓰는 항목
            item(span = { GridItemSpan(maxLineSpan)}){
                // span = 이 항목이 몇 칸을 차지할지
                // GridItemSpan(maxLineSpan) = 한 줄의 최대 칸 수만큼 = 2칸 전부 = 가로 꽉 차게

                // 메뉴 탭
                LazyRow{
                    // LazyRow = 가로로 스크롤되는 리스트
                    // 세로(Grid) 안에 가로(Row) → 방향이 달라서 겹쳐도 괜찮음

                    items(homeMenus) {menu ->
                        // homeMenus에서 메뉴 이름을 하나씩 꺼내서 menu에 담음

                        TabMenuItem(
                            text = menu,
                            isSelected = menu == selectedMenu,  // 내 이름 == 선택된 이름이면 선택!
                            onClick = { selectedMenu = menu },   // 누르면 선택된 이름을 내 이름으로 바꿈
                        )
                    }
                }
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

            // 제목
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 20.dp),  // 좌우 16, 배너와 20 띄움
                    text = "김솝트님을 위한 추천 아이템",
                    style = M18,
                    color = Black,
                )
            }

            // 카테고리 칩 (가로 스크롤)
            item(span = { GridItemSpan(maxLineSpan) }) {
                LazyRow(
                    modifier = Modifier.padding(top = 12.dp, bottom = 20.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    items(categories) { category ->
                        CategoryChip(
                            text = category,
                            isSelected = category == selectedCategory,
                            onClick = { selectedCategory = category },
                        )
                    }
                }
            }

            // 상품 카드 (2개씩 나란히)
            items(productList) { product ->
                // productList에서 상품을 하나씩 꺼내서 product에 담음
                ProductCard(product = product)
            }
        }

    }
}

// ✨ PR 캡처용: 메뉴 탭 한 줄 전체 미리보기
@Preview(showBackground = true, widthDp = 600) // widthDp = 미리보기 폭 (잘리면 숫자 키우기)
@Composable
private fun MenusPreview() {
    PlaySoptTheme {
        Row { // 탭들을 가로로 나란히
            listOf("2배적립", "홈", "셀렉티드", "브랜드", "뷰티", "라이프", "랭킹").forEach { menu ->
                TabMenuItem(
                    text = menu,
                    isSelected = menu == "홈", // "홈"만 선택된 모습
                    onClick = {}
                )
            }
        }
    }
}

// ✨ PR 캡처용: 카테고리 칩 한 줄 전체 미리보기
@Preview(showBackground = true, widthDp = 800)
@Composable
private fun CategoriesPreview() {
    PlaySoptTheme {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), // 바깥 여백
            horizontalArrangement = Arrangement.spacedBy(6.dp) // 칩 사이 간격
        ) {
            listOf("전체", "상의", "팬츠", "원피스", "아우터", "스커트", "슈즈", "가방").forEach { category ->
                CategoryChip(
                    text = category,
                    isSelected = category == "전체", // "전체"만 선택된 모습
                    onClick = {}
                )
            }
        }
    }
}