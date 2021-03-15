package com.example.emojigarden

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.realm.RealmQuery
import io.realm.kotlin.syncSession

class EmojiSelectorVm(application: Application) : AndroidViewModel(application) {

    var currentEmoji = mutableStateOf<EmojiTile?>(null, neverEqualPolicy())

    fun onEmojiClicked(emojiTile : EmojiTile) {
        currentEmoji.value = getApplication<EmojiGardenApplication>().realmModule.getSyncedRealm().copyFromRealm(emojiTile)
    }

    fun save() {
        getApplication<EmojiGardenApplication>().realmModule.getInsertRealm()
            .executeTransactionAsync { realm ->
                var existingClaim = realm.where(TileClaim::class.java).findFirst()
                if(existingClaim == null) {
                    existingClaim = TileClaim()
                }

                realm.insertOrUpdate(existingClaim.apply {
                    name = currentEmoji.value!!.name
                    bio = currentEmoji.value!!.bio
                    emoji = currentEmoji.value!!.emoji
                    tileId = currentEmoji.value!!._id
                    event = realm.syncSession.user.id
                })
            }

    }
}