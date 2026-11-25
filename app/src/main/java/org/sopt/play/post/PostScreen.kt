package org.sopt.play.post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.R
import org.sopt.play.component.PostBottomSheet
import org.sopt.play.component.PostCard
import org.sopt.play.component.SearchTextField
import org.sopt.play.ui.theme.PlaySoptTheme

@Composable
fun PostScreen(
    uiState: PostUiState,
    onKeywordChange: (String) -> Unit,
    onFabClick: () -> Unit,
    onSheetDismiss: () -> Unit,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onCompleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PlaySoptTheme.colors.white),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchTextField(
                value = uiState.keyword,
                onValueChange = onKeywordChange,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            )

            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 96.dp),
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

        FloatingActionButton(
            onClick = onFabClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 20.dp)
                .size(60.dp),
            shape = CircleShape,
            containerColor = PlaySoptTheme.colors.red,
            contentColor = PlaySoptTheme.colors.white,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_plus),
                contentDescription = "게시글 작성",
                modifier = Modifier.size(28.dp),
            )
        }

        if (uiState.isSheetVisible) {
            PostBottomSheet(
                title = uiState.title,
                content = uiState.content,
                isCompleteEnabled = uiState.isCompleteEnabled,
                onTitleChange = onTitleChange,
                onContentChange = onContentChange,
                onCompleteClick = onCompleteClick,
                onDismiss = onSheetDismiss,
            )
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
            onFabClick = {},
            onSheetDismiss = {},
            onTitleChange = {},
            onContentChange = {},
            onCompleteClick = {},
        )
    }
}
