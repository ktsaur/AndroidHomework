package ru.itis.homework6.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import ru.itis.homework6.R
import ru.itis.homework6.data.db.InceptionDatabase
import ru.itis.homework6.data.db.repository.UserRepository
import ru.itis.homework6.databinding.FragmentAuthorisationBinding
import ru.itis.homework6.di.ServiceLocator
import ru.itis.homework6.ui.AuthorisationText
import ru.itis.homework6.ui.LoginButton
import ru.itis.homework6.ui.MainText
import ru.itis.homework6.ui.PasswordTextField
import ru.itis.homework6.ui.RegistrationTextButton
import ru.itis.homework6.ui.UsernameTextField

class AuthorizationFragment:BaseFragment(R.layout.fragment_authorisation) {
    private val userRepository = ServiceLocator.getUserRepository()

    private val viewBinding: FragmentAuthorisationBinding by viewBinding(FragmentAuthorisationBinding::bind)

    private var username by mutableStateOf("")
    private var password by mutableStateOf("")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setView()
    }

    fun setView() {
        viewBinding.composeContainerId.setContent {
            Scaffold { padding ->
                Column (modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally)
                {
                    AuthorisationText()
                    UsernameTextField(
                        value = username,
                        onValueChange = { newValue ->
                            username = newValue }
                    )
                    PasswordTextField(
                        value = password,
                        onValueChange = { newValue ->
                            password = newValue }
                    )
                    LoginButton(onClick = {login()})
                    RegistrationTextButton (onClick = {registration()})
                }
            }
        }
    }

    fun login() { //тут надо проверить, если пользователь есть в системе, то отправляем на главную страницу, если нет то выходит ошибка
        if (username.isEmpty() ||  password.isEmpty()) {
            Toast.makeText(context, "All fields must be filled in!", Toast.LENGTH_SHORT).show()
            return
        }
        lifecycleScope.launch {
            val user = userRepository.getUserByUsernameAndPassword(username = username, password = password)
            if (user != null) {
                saveAuthorizationState(isLoggedIn = true)
                saveUserId(user.userId)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment())
                    .addToBackStack(null)
                    .commit()

                Toast.makeText(context, "You are logged in!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "The user does not exist.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun saveAuthorizationState(isLoggedIn: Boolean) {
        val sharedPreference = requireContext().getSharedPreferences("user_prefs", android.content.Context.MODE_PRIVATE)
        with(sharedPreference.edit()) {
            putBoolean("is_logged_in", isLoggedIn)
            apply()
        }
    }

    fun saveUserId(userId: String) {
        val sharedPreference = requireContext().getSharedPreferences("user_prefs", android.content.Context.MODE_PRIVATE)
        with(sharedPreference.edit()) {
            putString("user_id", userId)
            apply()
        }
    }

    fun registration() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, RegistrationFragment())
            .addToBackStack(null)
            .commit()
    }

}