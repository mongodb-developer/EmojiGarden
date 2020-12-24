package com.example.emojigarden

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import io.realm.*
import io.realm.kotlin.toFlow
import kotlinx.coroutines.flow.Flow
import androidx.compose.runtime.getValue

import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class EmojiVm(application: Application) : AndroidViewModel(application) {

    var emojiState : List<EmojiTile> by mutableStateOf(listOf())
        private set

     private val _emojiTiles : Flow<RealmResults<EmojiTile>> =  (application as EmojiGardenApplication).realmModule
         .getSyncedRealm()
         .where(EmojiTile::class.java)
         .findAllAsync()
         .toFlow()

    init {
        viewModelScope.launch {
            _emojiTiles.collect {
                emojiState = it
            }
        }
    }

}