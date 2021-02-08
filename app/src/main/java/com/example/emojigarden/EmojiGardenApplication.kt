package com.example.emojigarden

import android.app.Application

class EmojiGardenApplication : Application() {
    lateinit var realmModule : RealmModule

    override fun onCreate() {
        super.onCreate()

        // Get your appId from https://docs.mongodb.com/realm/get-started/find-your-app-id/
        val appId = "application-0-cpgih"
        realmModule = RealmModule(this, appId)
    }
}