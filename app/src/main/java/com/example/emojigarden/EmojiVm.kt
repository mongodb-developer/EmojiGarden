package com.example.emojigarden

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EmojiVm : ViewModel() {

    private val _emojiData : MutableLiveData<List<String>> = MutableLiveData(List(400) { "🥰" })

    val emojiData = _emojiData


}