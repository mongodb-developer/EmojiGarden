package com.example.emojigarden

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.lifecycle.AndroidViewModel
import io.realm.kotlin.syncSession
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class EmojiSelectorVm(application: Application) : AndroidViewModel(application) {

    var currentEmoji = mutableStateOf<EmojiTile?>(null, neverEqualPolicy())
    var currentTileType by mutableStateOf<TileType?>(null, neverEqualPolicy())
        private set

    private val myToken by lazy { getApplication<EmojiGardenApplication>().realmModule.getSyncedRealm().syncSession.user.id }

    fun onEmojiClicked(emojiTile: EmojiTile) {
        currentEmoji.value = getApplication<EmojiGardenApplication>().realmModule.getSyncedRealm()
            .copyFromRealm(emojiTile)
        currentTileType = tileType(emojiTile)
    }

    enum class TileType {
        EDITABLE,
        FREE_TILE_LIMIT_REACHED,
        OTHER_PERSONS_TILE
    }

    private fun tileType(emojiTile: EmojiTile): TileType =
        when (emojiTile.owner) {
            "", myToken -> {
                val tileClaim: TileClaim? =
                    getApplication<EmojiGardenApplication>().realmModule.getInsertRealm()
                        .where(TileClaim::class.java).findFirst()
                if (tileClaim != null && tileClaim.tileId != emojiTile._id) {
                    TileType.FREE_TILE_LIMIT_REACHED
                } else {
                    TileType.EDITABLE // still have to know if I have any tiles at all. If I did have them, the tile
                }
            }
            else -> TileType.OTHER_PERSONS_TILE
        }

    fun save() {
        getApplication<EmojiGardenApplication>().realmModule.getInsertRealm()
            .executeTransactionAsync { realm ->
                val existingClaim: TileClaim =
                    realm.where(TileClaim::class.java).findFirst() ?: TileClaim()

                realm.insertOrUpdate(existingClaim.apply {
                    name = currentEmoji.value!!.name
                    bio = currentEmoji.value!!.bio
                    emoji = currentEmoji.value!!.emoji
                    tileId = currentEmoji.value!!._id
                    event = realm.syncSession.user.id
                    rejected = false
                })

                currentEmoji.value = null
            }

    }
}