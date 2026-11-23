package org.sopt.play

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import org.sopt.play.navigation.MainNavHost
import org.sopt.play.ui.theme.PlaySoptTheme
import org.sopt.play.ui.theme.White

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // 상태바, 네비게이션바 영역까지 화면 꽉 채우기
        setContent {
            PlaySoptTheme {
                Scaffold(containerColor = White) { innerPadding ->
                    MainNavHost(
                        modifier = Modifier
                            .padding(innerPadding)
                            .consumeWindowInsets(innerPadding), // 키보드 중복 패딩 방지
                    )
                }
            }
        }
    }
}
