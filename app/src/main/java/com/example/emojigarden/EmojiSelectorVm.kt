package com.example.emojigarden

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import io.realm.kotlin.syncSession

class EmojiSelectorVm(application: Application) : AndroidViewModel(application) {

    var currentEmoji : EmojiTile? by mutableStateOf(null)
        private set

    fun onEmojiClicked(emojiTile : EmojiTile) {
        currentEmoji = getApplication<EmojiGardenApplication>().realmModule.getSyncedRealm().copyFromRealm(emojiTile)
    }

    /**
     * Maybe this should be in a new vm? Perhaps it's fine to remain in the existing one.
     * When a new emoji is selected both it and the old tile are passed in here.
     * The viewmodel will update the tile with the new emoji.
     */
    fun onNewEmojiSelected(newEmoji : String) {
        getApplication<EmojiGardenApplication>().realmModule.getInsertRealm()
            .executeTransactionAsync { realm ->

                realm.insertOrUpdate(TileClaim().apply {
                    tileId = currentEmoji!!._id
                    event = realm.syncSession.user.id
                    emoji = newEmoji
                })
                currentEmoji = null
            }
    }

    fun onBioUpdated(updatedName : String, updatedDescription : String) {
        getApplication<EmojiGardenApplication>().realmModule.getInsertRealm()
            .executeTransactionAsync { realm ->
                realm.insertOrUpdate(TileClaim().apply {
                refTile = currentEmoji!!.apply {
                    event = realm.syncSession.user.id
                    name = updatedName
                    bio = updatedDescription
                    owner = realm.syncSession.user.id
                }
                    event = realm.syncSession.user.id
                })
            }
    }
    // two tab UI. One to select the emoji. One for the name/description.

}