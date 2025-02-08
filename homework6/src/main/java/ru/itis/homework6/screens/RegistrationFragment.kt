package ru.itis.homework6.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import ru.itis.homework6.R
import ru.itis.homework6.data.db.entities.UserEntity
import ru.itis.homework6.databinding.FragmentRegistrationBinding
import ru.itis.homework6.di.ServiceLocator
import ru.itis.homework6.ui.AuthorizationTextButton
import ru.itis.homework6.ui.EmailTextField
import ru.itis.homework6.ui.PasswordTextField
import ru.itis.homework6.ui.PhoneTextField
import ru.itis.homework6.ui.RegistrationButton
import ru.itis.homework6.ui.RegistrationText
import ru.itis.homework6.ui.UsernameTextField
import java.util.UUID

class RegistrationFragment: BaseFragment(R.layout.fragment_registration) {
    private val userRepository = ServiceLocator.getUserRepository()

    private val viewBinding: FragmentRegistrationBinding by viewBinding(FragmentRegistrationBinding::bind)

    private var username by mutableStateOf("")
    private var email by mutableStateOf("")
    private var phone by mutableStateOf("")
    private var password by mutableStateOf("")


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    fun initViews() {
        viewBinding.composeContainerId.setContent {
            Scaffold { padding ->
                Column (modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally)
                {
                    RegistrationText()
                    UsernameTextField(
                        value = username,
                        onValueChange = { newValue ->
                            username = newValue }
                    )
                    EmailTextField(
                        value = email,
                        onValueChange = { newValue ->
                            email = newValue }
                    )
                    PhoneTextField(
                        value = phone ,
                        onValueChange = { newValue ->
                            phone = newValue }
                    )
                    PasswordTextField(
                        value = password,
                        onValueChange = { newValue ->
                            password = newValue }
                    )
                    RegistrationButton(onClick = {registration()})
                    AuthorizationTextButton(onClick = { authorization()})
                }
            }
        }
    }

    fun registration() {
        lifecycleScope.launch {
            val user = userRepository.getUserByUsernameAndPassword(username = username, password = password)
            if (user == null) {
                val newUser = UserEntity(
                    userId = UUID.randomUUID().toString(),
                    username = username,
                    email = email,
                    phone = phone,
                    password = password
                )
                userRepository.saveUser(newUser)
                saveUserId(newUser.userId)

                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment())
                    .addToBackStack(null)
                    .commit()

                Toast.makeText(context, "You have registered", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "User already exists", Toast.LENGTH_SHORT).show()
            }
        }
    }

     fun saveUserId(userId: String) {
        val sharedPreference = requireContext().getSharedPreferences("user_prefs", android.content.Context.MODE_PRIVATE)
        with(sharedPreference.edit()) {
            putString("user_id", userId)
            apply()
        }
    }

    fun authorization() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, AuthorizationFragment())
            .addToBackStack(null)
            .commit()
    }
}