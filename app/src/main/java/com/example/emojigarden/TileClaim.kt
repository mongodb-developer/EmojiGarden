package com.example.emojigarden

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.RealmModule
import org.bson.types.ObjectId

@RealmClass
open class TileClaim : RealmModel {
    @PrimaryKey
    var _id : ObjectId = ObjectId.get()
    var emoji = ""
    var event : String = ""
    var name = ""
    var bio = ""
    var tileId : ObjectId = ObjectId.get()
    var rejected : Boolean = false
}