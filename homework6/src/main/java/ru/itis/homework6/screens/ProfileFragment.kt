package ru.itis.homework6.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.itis.homework6.R

class ProfileFragment: BaseFragment(R.layout.fragment_profile){



    fun bundle(userId: String): Bundle = Bundle().apply {
        putString("USER_ID", userId)
    }
}