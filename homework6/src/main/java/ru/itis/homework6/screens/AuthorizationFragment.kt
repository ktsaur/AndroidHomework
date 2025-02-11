package ru.itis.homework6.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import ru.itis.homework6.R
import ru.itis.homework6.databinding.FragmentAuthorisationBinding
import ru.itis.homework6.di.ServiceLocator
import ru.itis.homework6.ui.LoginButton
import ru.itis.homework6.ui.PasswordTextField
import ru.itis.homework6.ui.RegistrationTextButton
import ru.itis.homework6.ui.Text
import ru.itis.homework6.ui.TextField
import ru.itis.homework6.util.PreferencesKeys

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
                    Text(text = "Authorization")
                    Spacer(modifier = Modifier.height(70.dp))
                    TextField(
                        value = username,
                        onValueChange = { newValue ->
                            username = newValue },
                        label = getString(R.string.username),
                        keyboardType = KeyboardType.Text
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
            Toast.makeText(context, context?.getString(R.string.fields_filled), Toast.LENGTH_SHORT).show()
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

                Toast.makeText(context, context?.getString(R.string.logged_in), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, context?.getString(R.string.user_not_exist), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun saveAuthorizationState(isLoggedIn: Boolean) {
        val sharedPreference = requireContext().getSharedPreferences(PreferencesKeys.USER_PREFS, android.content.Context.MODE_PRIVATE)
        with(sharedPreference.edit()) {
            putBoolean(PreferencesKeys.IS_LOGGED_IN, isLoggedIn)
            apply()
        }
    }

    fun saveUserId(userId: String) {
        val sharedPreference = requireContext().getSharedPreferences(PreferencesKeys.USER_PREFS, android.content.Context.MODE_PRIVATE)
        with(sharedPreference.edit()) {
            putString(PreferencesKeys.USER_ID, userId)
            apply()
        }
    }

    fun registration() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, RegistrationFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        composeView = null
    }

}