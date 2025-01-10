package ru.itis.homework4.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.itis.homework4.R
import ru.itis.homework4.Screens.NotificationFragment
import ru.itis.homework4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, NotificationFragment())
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}