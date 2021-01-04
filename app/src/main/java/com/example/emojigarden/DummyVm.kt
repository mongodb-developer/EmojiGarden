package com.example.emojigarden

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class DummyVm : ViewModel() {

    var emojiState : List<EmojiTile> by mutableStateOf(List(102){EmojiTile().apply  { emoji = "🍕" }})
        private set

}