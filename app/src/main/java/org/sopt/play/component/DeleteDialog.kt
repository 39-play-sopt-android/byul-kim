package org.sopt.play.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.sopt.play.ui.theme.PlaySoptTheme

@Composable
fun DeleteDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .width(288.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(PlaySoptTheme.colors.white)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Text(
                text = "게시글을 삭제할까요?",
                modifier = Modifier.fillMaxWidth(),
                style = PlaySoptTheme.typography.m18,
                color = PlaySoptTheme.colors.gray6,
                textAlign = TextAlign.Center,
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PlaySoptTheme.colors.gray1,
                        contentColor = PlaySoptTheme.colors.gray5,
                    ),
                    contentPadding = PaddingValues(16.dp),
                ) {
                    Text(text = "취소", style = PlaySoptTheme.typography.sb14)
                }

                SoptButton(
                    text = "삭제",
                    onClick = onConfirm,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Preview
@Composable
private fun DeleteDialogPreview() {
    PlaySoptTheme {
        DeleteDialog(
            onConfirm = {},
            onDismiss = {},
        )
    }
}
