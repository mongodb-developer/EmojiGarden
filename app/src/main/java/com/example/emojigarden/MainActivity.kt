package com.example.emojigarden

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.example.emojigarden.ui.EmojiGardenTheme

@ExperimentalFoundationApi
@ExperimentalLayout
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val loginAndDataInitVm : LoginAndDataInitVm = viewModel()

            if(loginAndDataInitVm.showGarden){
                val model : EmojiVmRealm = viewModel()
                MainActivityUi(model.emojiState)
            } else
            {
                LoginAndDataInitView(loginAndDataInitVm::login)
            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalLayout
@Composable
fun MainActivityUi(emojiList: List<EmojiTile>) {
    EmojiGardenTheme {
        Box(
            Modifier.fillMaxSize().padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            EmojiGrid(emojiList)
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalLayout
@Composable
fun EmojiGrid(emojiList: List<EmojiTile>) {

    LazyVerticalGrid(cells = GridCells.Adaptive(20.dp)) {
        items(emojiList) { emojiTile ->
            EmojiHolder(emojiTile)
        }
    }
}

@Composable
fun EmojiHolder(emoji: EmojiTile) {
    Text(emoji.emoji)
}

@Composable
fun LoginAndDataInitView(login : () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        Button(login){
            Text("Login")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun InitializationPreview() {
    LoginAndDataInitView{}
}

@ExperimentalFoundationApi
@ExperimentalLayout
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainActivityUi(List(102){EmojiTile().apply  { emoji = "😋" }})
}

