package com.example.emojigarden


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.emojigarden.ui.EmojiGardenTheme
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel


@ExperimentalFoundationApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val loginVm : LoginVm = viewModel()

            if(loginVm.showGarden){
                val model : EmojiVmRealm = viewModel()
                MainActivityUi(model.emojiState)
            } else
            {
                LoginView(loginVm::login)
            }

        }
    }
}

@ExperimentalFoundationApi
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

@Preview
@Composable
fun EmojiPreview() {
    EmojiHolder(EmojiTile().apply { emoji = "😼" })
}

@ExperimentalFoundationApi
@Composable
fun MainActivityUi(emojiList: List<EmojiTile>) {
    EmojiGardenTheme {
        Box(
            Modifier.fillMaxSize().padding(16.dp)
        ) {
            EmojiGrid(emojiList)
        }
    }
}

@Composable
fun LoginView(login : () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        Button(login){
            Text("Login")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun LoginPreview() {
    LoginView{}
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val emojis = listOf("🐤","🐦","🐔","🦤","🕊","️","🦆","🦅","🪶","🦩","🐥","-","🐣","🦉","🦜","🦚","🐧","🐓","🦢","🦃","🦡","🦇","🐻","🦫","🦬","🐈","‍","⬛","🐗","🐪","🐈","🐱","🐿","️","🐄","🐮","🦌","🐕","🐶","🐘","🐑","🦊","🦒","🐐","🦍","🦮","🐹","🦔","🦛","🐎","🐴","🦘","🐨","🐆","🦁","🦙","🦣","🐒","🐵","🐁","🐭","🦧","🦦","🐂","🐼","🐾","🐖","🐷","🐽","🐻","‍","❄","️","🐩","🐇","🐰","🦝","🐏","🐀","🦏","🐕","‍","🦺","🦨","🦥","🐅","🐯","🐫","-","🦄","🐃","🐺","🦓","🐳","🐡","🐬","🐟","🐙","🦭","🦈","🐚","🐳","🐠","🐋","🌱","🌵","🌳","🌲","🍂","🍀","🌿","🍃","🍁","🌴","🪴","🌱","☘","️","🌾","🐊","🐊","🐉","🐲","🦎","🦕","🐍","🦖","-","🐢")
    MainActivityUi(List(102){ i -> EmojiTile().apply  { emoji = emojis[i] }})
}
