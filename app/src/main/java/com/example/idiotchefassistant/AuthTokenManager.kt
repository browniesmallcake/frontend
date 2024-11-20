package com.example.idiotchefassistant
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

class AuthTokenManager(private val context: Context) {
    private val tokenKey = stringPreferencesKey("auth_token")

    // Read the token
    val authToken: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[tokenKey]
        }

    // save the token
    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[tokenKey] = token
        }
    }

    // remove the token
    suspend fun clearAuthToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(tokenKey)
        }
    }
}
