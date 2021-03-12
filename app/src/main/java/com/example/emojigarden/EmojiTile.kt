package com.example.emojigarden

import io.realm.RealmModel
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
    var name : String = ""
    var bio : String = ""
    var owner : String = ""
}

// Each emoji tile needs an owner. And sometimes that owner is "public" which is what tiles added from the server could be assigned.
// When a time is claimed its owner is changed.
// Server side rules will only allow changes to "yourid" or "owner". Each time an emoji is inserted or updated its "owner" field is checked
// to make sure that someone who doesn't own the tile can't set it owner.
