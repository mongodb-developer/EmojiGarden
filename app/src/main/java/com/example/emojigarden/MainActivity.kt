package com.example.emojigarden

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.emojigarden.ui.EmojiGardenTheme

@ExperimentalLayout
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityUi(emptyList())
        }
    }
}

@ExperimentalLayout
@Composable
fun MainActivityUi(emojiList: List<String>) {
    EmojiGardenTheme {
        Surface(color = MaterialTheme.colors.background) {
            Box(
                Modifier.fillMaxWidth().fillMaxHeight().padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                EmojiGrid(emojiList)
            }
        }
    }
}

@ExperimentalLayout
@Composable
fun EmojiGrid(emojiList: List<String>) {
    FlowRow {
        emojiList.forEach {
            EmojiHolder(it)
        }
    }
}

@Composable
fun EmojiHolder(emoji: String) {
    Text(emoji)
}

@ExperimentalLayout
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val emojiList = MutableList(400) { "😋" }
    MainActivityUi(emojiList)
}