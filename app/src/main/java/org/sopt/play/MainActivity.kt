package org.sopt.play

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.sopt.play.navigation.MainNavHost
import org.sopt.play.ui.theme.PlaySoptTheme
import org.sopt.play.ui.theme.White

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
          setContent {
              val viewModel by viewModels<MainViewModel>()
              val context = LocalContext.current

              LaunchedEffect(Unit) {
                  viewModel.sideEffect.collect { effect ->
                      when(effect) {
                          is MainSideEffect.ShowToast -> {
                              Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                          }
                      }
                  }
              }

              PlaySoptTheme {
                  Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                      PartScreen(
                          onPartClick = viewModel::onPartClick,
                          modifier = Modifier.padding(innerPadding),
                      )
                  }
              }
          }
    }
}
