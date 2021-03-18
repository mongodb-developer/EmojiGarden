package com.example.emojigarden

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import io.realm.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.setValue
import io.realm.kotlin.syncSession

class EmojiVmRealm(application: Application) : AndroidViewModel(application) {

    /** emojiState which can be used in compose directly.
     * can't be set from outside EmojiVm since it has a private setter.
     * If using realm change listeners, the default Structural Quality (='s check) doesn't see a difference between RealmResults,
     *      so use neverEqual. Anyway the change listener will only be called for changes.
     * This value is going to be updated via change listener on the EmojiTile RealmResults.
     */
    var emojiState : List<EmojiTile> by mutableStateOf(listOf(), neverEqualPolicy())
        private set

    fun isOwnTile(emojiTile: EmojiTile) : Boolean {
        return emojiTile.owner == myToken
    }

    /** Changes are set to the current list, the changeset isn't required so it's marked _
     */
    private val emojiTilesChangeListener  =
        OrderedRealmCollectionChangeListener<RealmResults<EmojiTile>> { updatedResults, _ ->
        emojiState = updatedResults.freeze()
    }

    private val myToken : String by lazy { getApplication<EmojiGardenApplication>().realmModule.getSyncedRealm().syncSession.user.id }

    private val emojiTilesResults : RealmResults<EmojiTile> =  getApplication<EmojiGardenApplication>().realmModule
         .getSyncedRealm()
         .where(EmojiTile::class.java)
         .sort(EmojiTile::index.name)
         .findAllAsync()
         .apply {
             addChangeListener(emojiTilesChangeListener)
         }

    override fun onCleared() {
        super.onCleared()
        // All change listeners need to be removed when shutting down
        emojiTilesResults.removeAllChangeListeners()
    }

}