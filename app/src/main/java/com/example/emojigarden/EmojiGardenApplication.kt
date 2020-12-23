package com.example.emojigarden

import android.app.Application

class EmojiGardenApplication : Application() {
    lateinit var realmModule : RealmModule

    override fun onCreate() {
        super.onCreate()
        realmModule = RealmModule(this)
    }
}