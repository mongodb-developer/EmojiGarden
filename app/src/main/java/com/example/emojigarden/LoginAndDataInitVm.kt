package com.example.emojigarden

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

/**
 * Methods to:
 * 1. Login anonymously with MongoDb Realm Sync
 * 2. Send initial data to MongoDB Realm Sync
 *
 * A bit of state to ensure initial data isn't attempted to be sent unless you're logged in.
 */
class LoginAndDataInitVm(application: Application) : AndroidViewModel(application) {
    private val TAG = LoginAndDataInitVm::class.java.simpleName

    var showGarden : Boolean by mutableStateOf(getApplication<EmojiGardenApplication>().realmModule.isInitialized())
        private set

    fun login() =
        getApplication<EmojiGardenApplication>().realmModule.loginAnonSyncedRealm(
            onSuccess = ::initializeData, // If login succeeds, initialize data within the app
            onFailure = { Log.d(TAG, "Failed to login") } // Just log failures
        )

    private fun initializeData() {
        getApplication<EmojiGardenApplication>().realmModule.initializeCollectionIfEmpty()
        showGarden = true
    }
}
