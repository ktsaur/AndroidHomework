/*
package ru.itis.homework6.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.itis.homework6.R
import ru.itis.homework6.data.db.entities.UserEntity
import ru.itis.homework6.databinding.FragmentProfileBinding
import ru.itis.homework6.di.ServiceLocator
import ru.itis.homework6.ui.BottomSheetDemo
import ru.itis.homework6.ui.DeleteProfileButton
import ru.itis.homework6.ui.EmailText
import ru.itis.homework6.ui.LogOutButton
import ru.itis.homework6.ui.PasswordText
import ru.itis.homework6.ui.PhoneText
import ru.itis.homework6.ui.ProfileText
import ru.itis.homework6.ui.UsernameText


class ProfileFragment(val user: UserEntity): BaseFragment(R.layout.fragment_profile){

    private val userRepository = ServiceLocator.getUserRepository()

    private val viewBinding: FragmentProfileBinding by viewBinding(FragmentProfileBinding::bind)

    private val user =

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i("ProfileFragment--TAG", "username = ${user.username}")
        setView()
    }

    fun setView(){
        viewBinding.composeContainerId.setContent {
            BottomSheetDemo(
                username = user.username,
                email = user.email,
                phone = user.phone,
                password = user.password,
                onSave = { username, email, phone, password ->
                    onSave(username, email, phone, password ) }
            )
            Column {
                ProfileText()
                UsernameText(username = user.username)
                EmailText(email = user.email)
                PhoneText(phone = user.phone)
                PasswordText(password = user.password)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    LogOutButton { logout() }
                    DeleteProfileButton { delete() }
                }
            }


        }
    }

    fun onSave(username: String, email: String, phone: String, password: String){
        lifecycleScope.launch {
            userRepository.updateUserInfo(user.userId, username, email, phone, password)
        }
    }

    fun logout(){
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", android.content.Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()

        parentFragmentManager.beginTransaction()
            .replace(R.id.container, AuthorizationFragment())
            .addToBackStack(null)
            .commit()
    }



    fun delete() {
        lifecycleScope.launch {
            userRepository.deleteUser(user = user)
        }
    }

}*/
