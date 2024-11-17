package ru.itis.homework2.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.itis.homework2.R
import ru.itis.homework2.databinding.ActivityMainBinding
import ru.itis.homework2.fragments.MainFragment

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment())
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}