package com.example.appeventtracker.sdk

import android.content.Context
import java.util.UUID

object InstallationManager {

    private const val PREF_NAME = "analytics_sdk_prefs"
    private const val KEY_INSTALLATION_ID = "installation_id"

    fun getInstallationId(context: Context): String {

        val preferences = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )

        val existingId = preferences.getString(KEY_INSTALLATION_ID, null)

        if (existingId != null) {
            return existingId
        }

        val newId = UUID.randomUUID().toString()

        preferences.edit()
            .putString(KEY_INSTALLATION_ID, newId)
            .apply()

        return newId
    }
}