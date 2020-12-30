package com.example.emojigarden

import android.app.Application
import android.util.Log

import io.realm.Realm
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.Credentials
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration
import java.lang.IllegalStateException

/**
 * Manages the setup for a synced realm db.
 * Limitations: This currently requires the user to be online the first time they use the app.
 */
class RealmModule(application: Application) {
    private var syncedRealm: Realm? = null
    private lateinit var app : App
    private val TAG = RealmModule::class.java.simpleName

    // Get this from https://realm.mongodb.com/ for the database you created under there.
    private val appId = "emojigarden-cmwby"

    // Setup the thing you'd always need to do.
    private fun setupLocalRealm(applicationContext: Application) {
        Realm.init(applicationContext) // Same for local and remote
        app = App(AppConfiguration.Builder(appId).build())
    }

    init {
        Log.d(TAG, "Setting up realm")
        setupLocalRealm(application)
        loginAnonSyncedRealm(
            onSuccess = {Log.d(TAG, "Login successful") },
            onFailure = {Log.d(TAG, "Login Unsuccessful, are you connected to the net?")}
        )

    }

    fun loginAnonSyncedRealm(organization : String = "default", onSuccess : () -> Unit, onFailure : () -> Unit ) {

        check(!Boolean.equals(app.currentUser()?.isLoggedIn)) {
            "Attempted to login again after login was already successful"
        }

        val credentials = Credentials.anonymous()

        app.loginAsync(credentials) { loginResult ->
            Log.d("RealmModule", "logged in: $loginResult, error? : ${loginResult.error}")
            if (loginResult.isSuccess) {
                instantiateSyncedRealm(loginResult.get(), organization)
                onSuccess()
            } else {
                onFailure()
            }
        }

    }

    fun getSyncedRealm() : Realm = syncedRealm ?: throw IllegalStateException("loginAnonSyncedRealm has to return onSuccess first")

    private fun instantiateSyncedRealm(user: User?, partition : String) {
        val config: SyncConfiguration = SyncConfiguration.defaultConfig(user, partition)
        syncedRealm = Realm.getInstance(config)
    }
}