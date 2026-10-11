package org.sopt.play

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.sopt.play.component.PlaySoptTopBar
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
    ){innerPadding ->}
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "홈")
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview(){
    PlaySoptTheme {
        HomeScreen()
    }
}