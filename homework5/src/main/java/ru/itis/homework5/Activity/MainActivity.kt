package ru.itis.homework5.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.itis.homework5.Fragment.ComposeSampleFragment
import ru.itis.homework5.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ComposeSampleFragment())
            .commit()
    }
}