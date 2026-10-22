package org.sopt.play.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.R
import org.sopt.play.ui.theme.PlaySoptTheme

@Composable
fun PlaySoptTopBar(modifier: Modifier = Modifier){
    Row( //가로로 나란히 배치
        modifier = modifier
            .fillMaxWidth() //가로로 꽉 채우기
            .background(PlaySoptTheme.colors.white) //배경 흰색
            .padding(start = 16.dp, end = 6.dp) , //여백
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,      // 글자랑 아이콘 세로 가운데 맞추기
    ) {
        //왼쪽 앱 이름
        Text(
            text = "PLAY SOPT",
            style = PlaySoptTheme.typography.b28,
        )

        // 오른쪽 검색 아이콘
        // painterResource: res 폴더의 그림 파일을 불러오는 함수
        Icon(
            painter = painterResource(R.drawable.ic_search),
            contentDescription = "검색", // 의미 있는 아이콘이라 설명을 씀 (꾸밈용이면 null)
            tint = PlaySoptTheme.colors.black,
            modifier = Modifier
                .padding(10.dp) // 순서 중요! padding 먼저 → 바깥 여백
                .size(28.dp),   // 피그마 실제 사용 크기 28
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PlaySoptTopBarPreview(){
    PlaySoptTheme { // 앱 테마 입히기
        PlaySoptTopBar() // 탑바 그리기
    }
}
