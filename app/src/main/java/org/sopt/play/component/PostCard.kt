package org.sopt.play.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.post.Post
import org.sopt.play.ui.theme.PlaySoptTheme

// 게시글 카드 하나
@Composable
fun PostCard(
    post: Post, // 보여줄 게시글
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(PlaySoptTheme.colors.gray1)
            .padding(20.dp),
    ) {
        // 작성자 | 날짜
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = post.author,
                style = PlaySoptTheme.typography.m12,
                color = PlaySoptTheme.colors.gray3,
            )
            Box(
                modifier = Modifier
                    .size(width = 1.dp, height = 12.dp)
                    .background(PlaySoptTheme.colors.gray3),
            )
            Text(
                text = post.createdDate,
                style = PlaySoptTheme.typography.m12,
                color = PlaySoptTheme.colors.gray3,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = post.title,
            style = PlaySoptTheme.typography.m16,
            color = PlaySoptTheme.colors.gray5,
        )

        Spacer(modifier = Modifier.height(4.dp)) // 제목 ↔ 본문 4

        Text(
            text = post.content,
            style = PlaySoptTheme.typography.m14,
            color = PlaySoptTheme.colors.gray5,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PostCardPreview() {
    PlaySoptTheme {
        PostCard(
            post = Post(
                id = 1,
                title = "아 배고파",
                content = "두바이는 왜케 마싯는걸까 두바이 와플 먹고싶어요",
                author = "벼리짱",
                createdDate = "2026.11.23",
            ),
        )
    }
}
