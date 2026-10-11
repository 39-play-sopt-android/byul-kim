package org.sopt.play


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.ui.theme.PlaySoptTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlaySoptTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MusicChart(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

data class Music(
    val rank: Int,
    val title: String,
    val name: String,
)

val musicList = listOf(
    Music(
        rank = 1,
        title = "TOO BAD",
        name = "G-DRAGON",
    ),
    Music(
        rank = 2,
        title = "모르시나요",
        name = "조째즈",
    ),
    Music(
        rank = 3,
        title = "like JENNIE",
        name = "제니 (JENNIE)",
    ),
    Music(
        rank = 4,
        title = "Drowning",
        name = "WOODZ",
    ),
    Music(
        rank = 5,
        title = "HOME SWEET HOME",
        name = "G-DRAGON",
    ),
    Music(
        rank = 6,
        title = "HOME SWEET HOME",
        name = "G-DRAGON",
    ),
    Music(
        rank = 7,
        title = "REBEL HEART",
        name = "IVE (아이브)",
    ),
    Music(
        rank = 8,
        title = "Whiplash",
        name = "aespa",
    ),
    Music(
        rank = 9,
        title = "오늘만 I LOVE YOU",
        name = "BOYNEXTDOOR",
    ),
    Music(
        rank = 10,
        title = "APT",
        name = "로제 (ROSE), Bruno Mars",
    ),
)

@Composable
fun MusicChart(modifier: Modifier){
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ){
        itemsIndexed(musicList) { index, music->
            MusicContainer(music, index)
        }
    }
}

@Composable
fun MusicContainer(
    music: Music,
    index: Int
){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = (index+1).toString()
        )
        Column {
            Text(
                text = music.title
            )
            Text(
                text = music.name
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ColorLazyRowPreview(){
    PlaySoptTheme() {
        MusicChart(modifier = Modifier)
    }
}


