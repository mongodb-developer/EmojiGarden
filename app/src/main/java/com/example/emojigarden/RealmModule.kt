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
 *
 * Steps that this class carries out when instantiated:
 * 1. A local realm is initialized.
 * 2. It assumes you're online, and tries to login with an anonymous user.
 * 3. If successful, it uses that logged-in user to open a synced realm.
 * 4. The Synced realm is made available via the getSyncedRealm function.
 *
 * All That's needed
 */
class RealmModule(application: Application, appId : String) {
    private var syncedRealm: Realm? = null
    private val app : App
    private val TAG = RealmModule::class.java.simpleName

    init {
        Log.d(TAG, "Setting up realm")

        Realm.init(application) // Required for local or remote.
        app = App(AppConfiguration.Builder(appId).build())

        // Login anonymously because a logged in user is required to open a synced realm.
        loginAnonSyncedRealm(
            onSuccess = {Log.d(TAG, "Login successful") },
            onFailure = {Log.d(TAG, "Login Unsuccessful, are you connected to the net?")}
        )
    }

    private fun loginAnonSyncedRealm(organization : String = "default", onSuccess : () -> Unit, onFailure : () -> Unit ) {

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