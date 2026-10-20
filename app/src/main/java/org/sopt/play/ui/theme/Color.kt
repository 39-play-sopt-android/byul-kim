package org.sopt.play.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ───── 피그마 색깔 ─────
val Black = Color(0xFF121212)
val Gray1 = Color(0xFFF7F7F7)
val Gray2 = Color(0xFFD1D5D6)
val Gray3 = Color(0xFFB2BABD)
val Gray5 = Color(0xFF505559)
val Gray6 = Color(0xFF23272A)
val White = Color(0xFFFFFFFF)
val Red = Color(0xFFFF4D4D)

@Immutable //Compose에게 이 값은 안 바뀌어라고 알려줘서, 쓸데없이 화면을 다시 그리지 않게 함
data class PlaySoptColors( //흩어져 있던 색들을 한 상자에 모은 것
    val black: Color = Black,
    val gray1: Color = Gray1,
    val gray2: Color = Gray2,
    val gray3: Color = Gray3,
    val gray5: Color = Gray5,
    val gray6: Color = Gray6,
    val white: Color = White,
    val red: Color = Red,
)

val LocalPlaySoptColors = staticCompositionLocalOf { PlaySoptColors() }
//staticCompositionLocalOf 이 상자를 아래로 내려보내는 통로