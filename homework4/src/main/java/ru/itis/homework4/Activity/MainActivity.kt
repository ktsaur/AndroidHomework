package ru.itis.homework4.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.itis.homework4.Notification.NotificationHandler
import ru.itis.homework4.R
import ru.itis.homework4.Screens.MainFragment
import ru.itis.homework4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var notificationHandler: NotificationHandler? = null
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        initViews()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment())
            .commit()
        initNotification()
    }

    fun initViews() {
        val selectedTheme = intent.getIntExtra(getString(R.string.current_theme), R.style.Base_Theme_AndroidHomework)
        setTheme(selectedTheme)
    }

    fun initNotification() {
        if(notificationHandler == null) {
            notificationHandler = NotificationHandler(this.applicationContext)
        }
        if (intent.getBooleanExtra(getString(R.string.open_from_notification), false)) {
            Toast.makeText(this, getString(R.string.app_open_from_notification),  Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}