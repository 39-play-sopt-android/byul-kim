package org.sopt.play

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.component.PlaySoptTopBar
import org.sopt.play.ui.theme.Gray2
import org.sopt.play.ui.theme.PlaySoptTheme
import org.sopt.play.ui.theme.White

// 홈 화면 (로그인 성공 후 도착하는 화면)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

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
        ){ // 이 안에서는 item { }, items { }로 항목을 등록함

            // 한 줄을 통째로 쓰는 항목
            item(span = { GridItemSpan(maxLineSpan)}){
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "메뉴/배너/제목/칩 자리" //임시
                )
            }

            // 한 칸씩 들어가는 항목 (2개씩 나란히)
            items(20){
                // items(개수) = 똑같은 걸 20개 만들기 (테스트용)

                Box(
                    modifier = Modifier
                        .padding(4.dp)        // 네모끼리 붙지 않게 4씩 띄우기
                        .height(150.dp)       // 높이 150 (가로는 Grid가 알아서 반 칸으로 맞춰줌)
                        .background(Gray2),   // 회색으로 칠해서 눈에 보이게

                )
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview(){
    PlaySoptTheme {
        HomeScreen()
    }
}