package com.example.mvpapplication

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import com.example.mvpapplication.model.network.Network.RAWG_API_KEY
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.ktx.remoteConfig

class AppApplication : Application(), ActivityLifecycleCallbacks {
    companion object {
        private const val RAWG_KEY_CONFIG = "RAWG_API_KEY"
    }

    private var isFetched = false
    private lateinit var remoteConfig: FirebaseRemoteConfig

    override fun onCreate() {
        super.onCreate()
        remoteConfig = Firebase.remoteConfig
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (!isFetched) {
            remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    RAWG_API_KEY = remoteConfig.getString(RAWG_KEY_CONFIG)
                    isFetched = true
                }
            }

            remoteConfig.addOnConfigUpdateListener(
                object : ConfigUpdateListener {
                    override fun onUpdate(configUpdate: ConfigUpdate) {
                        if (configUpdate.updatedKeys.contains(RAWG_KEY_CONFIG)) {
                            remoteConfig.activate().addOnCompleteListener {
                                RAWG_API_KEY = remoteConfig.getString(RAWG_KEY_CONFIG)
                            }
                        }
                    }

                    override fun onError(error: FirebaseRemoteConfigException) {}
                })
        }
    }

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}
}
