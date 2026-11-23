package org.sopt.play.post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.sopt.play.ui.theme.PlaySoptTheme

// 게시글 화면 그리기 담당
@Composable
fun PostScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize() // 화면 꽉 채우기
            .background(PlaySoptTheme.colors.white), // 배경 흰색
        contentAlignment = Alignment.Center, // 안의 내용을 가운데에
    ) {
        Text(
            text = "게시글 화면",
            style = PlaySoptTheme.typography.m18,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PostScreenPreview() {
    PlaySoptTheme {
        PostScreen()
    }
}
