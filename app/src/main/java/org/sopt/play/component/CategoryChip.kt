package org.sopt.play.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.ui.theme.Gray2
import org.sopt.play.ui.theme.Gray5
import org.sopt.play.ui.theme.M14
import org.sopt.play.ui.theme.PlaySoptTheme
import org.sopt.play.ui.theme.White

//카테고리 칩

@Composable
fun CategoryChip(
    modifier: Modifier = Modifier,
    text: String,              // 칩 이름
    isSelected: Boolean,       // 선택됐는지 (밖에서 알려줌)
    onClick: () -> Unit,       // 눌렸을 때 할 일 (밖에서 정함)
){
    val shape = RoundedCornerShape(6.dp)   // 모서리 둥글기 6

    Box( // 글자 하나를 가운데 놓는 상자
        modifier = modifier
            .height(32.dp)
            .clip(shape)
            .background(if (isSelected) Gray5 else White)
            .border(1.dp, if (isSelected) Gray5 else Gray2, shape)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
        text = text,
        style = M14,
        color = if (isSelected) White else Gray5,
        )
    }
}


@Preview
@Composable
private fun CategoryChipPrview(){
    PlaySoptTheme{
        Row {
            CategoryChip(text = "전체", isSelected = true, onClick = {})
            CategoryChip(text = "전체", isSelected = false, onClick = {})
        }
    }
}