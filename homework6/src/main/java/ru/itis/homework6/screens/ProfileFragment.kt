package ru.itis.homework6.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import ru.itis.homework6.R
import ru.itis.homework6.data.db.entities.UserEntity
import ru.itis.homework6.databinding.FragmentProfileBinding
import ru.itis.homework6.di.ServiceLocator
import ru.itis.homework6.ui.BottomSheetDemo
import ru.itis.homework6.ui.ProfileButton
import ru.itis.homework6.ui.ProfileScreen
import ru.itis.homework6.ui.ProfileText
import ru.itis.homework6.util.PreferencesKeys


class ProfileFragment: BaseFragment(R.layout.fragment_profile){

    private val userRepository = ServiceLocator.getUserRepository()
    private var user by mutableStateOf<UserEntity?>(null)
    private val viewBinding: FragmentProfileBinding by viewBinding(FragmentProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()

        val userId = getUserId()
        loadUserData(userId)

        Log.i("ProfileFragment--TAG", "username = ${user?.username}")
    }

    fun setView(){
        viewBinding.composeContainerId.setContent {
            user?.let {
                BottomSheetDemo(
                    username = it.username,
                    email = it.email,
                    phone = it.phone,
                    password = it.password,
                    onSave = { username, email, phone, password ->
                        onSave(username, email, phone, password ) }
                )
            }

            Column {
                ProfileText()
                ProfileScreen(userState = user)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    ProfileButton (onClick = {logout()}, text = getString(R.string.logout) )
                    Spacer(modifier = Modifier.width(25.dp))
                    ProfileButton (onClick = {delete()}, text = getString(R.string.delete) )
                }
            }
        }
    }

    fun onSave(username: String, email: String, phone: String, password: String){
        lifecycleScope.launch {
            user?.let {
                userRepository.updateUserInfo(it.userId, username, email, phone, password)
                Toast.makeText(context, context?.getString(R.string.change_saved), Toast.LENGTH_SHORT).show()
            }

            val userId = getUserId()
            loadUserData(userId)

        }
    }

    fun logout(){
        val sharedPreferences = requireContext().getSharedPreferences(PreferencesKeys.USER_PREFS, android.content.Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()

        parentFragmentManager.beginTransaction()
            .replace(R.id.container, AuthorizationFragment())
            .addToBackStack(null)
            .commit()
    }

    fun getUserId(): String? {
        val sharedPreference = requireContext().getSharedPreferences(PreferencesKeys.USER_PREFS,  android.content.Context.MODE_PRIVATE)
        return sharedPreference.getString(PreferencesKeys.USER_ID, null)
    }

    private fun loadUserData(userId: String?) {
        lifecycleScope.launch {
            if(userId != null) {
                val fetchedUser = userRepository.getUserById(userId)
                user = fetchedUser
            }
        }
    }

    fun delete() {
        lifecycleScope.launch {
            user?.let {
                userRepository.deleteUser(user = it)
                Toast.makeText(context, context?.getString(R.string.user_deleted), Toast.LENGTH_SHORT).show()
            }
        }

        val sharedPreferences = requireContext().getSharedPreferences(PreferencesKeys.USER_PREFS, android.content.Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()

        parentFragmentManager.beginTransaction()
            .replace(R.id.container, AuthorizationFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        composeView = null
    }

}
