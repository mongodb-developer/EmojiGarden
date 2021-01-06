package com.example.emojigarden

import android.app.Application

class EmojiGardenApplication : Application() {
    lateinit var realmModule : RealmModule

    override fun onCreate() {
        super.onCreate()

        // Get your appId from https://realm.mongodb.com/ for the database you created under there.
        val appId = "emojigarden-svbas"
        realmModule = RealmModule(this, appId)
    }
}