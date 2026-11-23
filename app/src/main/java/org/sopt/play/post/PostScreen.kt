package org.sopt.play.post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.component.PostCard
import org.sopt.play.component.SearchTextField
import org.sopt.play.ui.theme.PlaySoptTheme

@Composable
fun PostScreen(
    uiState: PostUiState,
    onKeywordChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PlaySoptTheme.colors.white),
    ) {
        SearchTextField(
            value = uiState.keyword,
            onValueChange = onKeywordChange,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        )

        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(
                items = uiState.posts,
                key = { post -> post.id },
            ) { post ->
                PostCard(post = post)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PostScreenPreview() {
    PlaySoptTheme {
        PostScreen(
            uiState = PostUiState(
                posts = listOf(
                    Post(
                        id = 2,
                        title = "애니 추천 좀",
                        content = "반딧불이의 혼례 아시나요 요즘은 그거 보는 중인데 더 쩌는 애니 추천해주세요 요즘 애니 권태기 옴",
                        author = "나",
                        createdDate = "2026.11.24",
                    ),
                    Post(
                        id = 1,
                        title = "아 배고파",
                        content = "두바이는 왜케 마싯는걸까 두바이 와플 먹고싶어요",
                        author = "벼리짱",
                        createdDate = "2026.11.23",
                    ),
                ),
            ),
            onKeywordChange = {},
        )
    }
}
