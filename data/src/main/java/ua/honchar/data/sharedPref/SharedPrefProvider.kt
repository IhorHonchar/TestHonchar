package ua.honchar.data.sharedPref

import android.content.Context

interface SharedPrefProvider {
    fun saveToken(token: String)
    fun getToken(): String
}

class SharedPrefProviderImpl(context: Context): SharedPrefProvider {

    private val prefs = context.applicationContext
        .getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

    override fun saveToken(token: String) =
        prefs.edit().putString(TOKEN, token).apply()


    override fun getToken(): String = prefs.getString(TOKEN, "").orEmpty()

    companion object {
        private const val SHARED_PREFS = "testApp"
        private const val TOKEN = "token"
    }
}