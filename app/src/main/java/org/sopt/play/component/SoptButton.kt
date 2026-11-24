package org.sopt.play.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.ui.theme.PlaySoptTheme

@Composable
fun SoptButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = PlaySoptTheme.colors.black,
            contentColor = PlaySoptTheme.colors.gray1,
            disabledContainerColor = PlaySoptTheme.colors.gray1,
            disabledContentColor = PlaySoptTheme.colors.gray3,
        ),
        contentPadding = PaddingValues(16.dp),
    ) {
        Text(text = text, style = PlaySoptTheme.typography.sb14)
    }
}

@Preview(showBackground = true)
@Composable
private fun SoptButtonPreview() {
    PlaySoptTheme {
        Column {
            SoptButton(text = "완료", onClick = {}, enabled = true)
            SoptButton(text = "완료", onClick = {}, enabled = false)
        }
    }
}
