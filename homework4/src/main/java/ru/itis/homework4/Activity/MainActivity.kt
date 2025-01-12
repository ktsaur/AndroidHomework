package ru.itis.homework4.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.itis.homework4.Notification.NotificationHandler
import ru.itis.homework4.R
import ru.itis.homework4.Screens.NotificationFragment
import ru.itis.homework4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var notificationHandler: NotificationHandler? = null
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val selectedTheme = intent.getStringExtra("selected_theme")

        when(selectedTheme) {
            "sand" -> setTheme(R.style.SandTheme_AndroidHomework)
            "blue" -> setTheme(R.style.BlueTheme_AndroidHomework)
            "green" -> setTheme(R.style.GreenTheme_AndroidHomework)
            "base" -> setTheme(R.style.Base_Theme_AndroidHomework)
        }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, NotificationFragment())
            .commit()
        if(notificationHandler == null) {
            notificationHandler = NotificationHandler(this.applicationContext)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}