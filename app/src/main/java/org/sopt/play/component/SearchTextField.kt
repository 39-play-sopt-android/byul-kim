package org.sopt.play.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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

// 게시글 화면 맨 위 검색창
@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "검색어를 입력하세요",
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = PlaySoptTheme.typography.m18.copy(color = PlaySoptTheme.colors.gray6),
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = PlaySoptTheme.colors.gray2,
                shape = RoundedCornerShape(12.dp),
            ),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    // weight(1f) = 아이콘 자리 빼고 남은 가로 전부 차지
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = PlaySoptTheme.typography.m18,
                            color = PlaySoptTheme.colors.gray2,
                        )
                    }
                    innerTextField()
                }

                Spacer(modifier = Modifier.width(10.dp))

                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = "검색",
                    tint = PlaySoptTheme.colors.gray3,
                    modifier = Modifier.size(24.dp),
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchTextFieldPreview() {
    PlaySoptTheme {
        SearchTextField(value = "", onValueChange = {})
    }
}
