package ru.itis.homework6.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.itis.homework6.R
import ru.itis.homework6.screens.AuthorizationFragment
import ru.itis.homework6.screens.MainFragment
import ru.itis.homework6.screens.RegistrationFragment
import ru.itis.homework6.util.PreferencesKeys

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if(isUserLoggedIn()) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment())
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AuthorizationFragment())
                .commit()
        }
    }

    fun isUserLoggedIn(): Boolean {
        val sharedPreference = getSharedPreferences(PreferencesKeys.USER_PREFS, android.content.Context.MODE_PRIVATE)
        return sharedPreference.getBoolean(PreferencesKeys.IS_LOGGED_IN, false)
    }
}