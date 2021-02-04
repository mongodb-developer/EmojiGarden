package com.example.emojigarden

import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import org.bson.types.ObjectId

@RealmClass
open
class EmojiTile : RealmModel {
    @PrimaryKey
    var _id : ObjectId = ObjectId.get()
    var index = 0
    var emoji : String = ""
    var event : String = "default" // This will be the partition key (separates synced realms so different organizations can have different gardens)
}