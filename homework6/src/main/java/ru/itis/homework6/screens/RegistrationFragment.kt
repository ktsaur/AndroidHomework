package ru.itis.homework6.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import ru.itis.homework6.R
import ru.itis.homework6.data.db.entities.UserEntity
import ru.itis.homework6.databinding.FragmentRegistrationBinding
import ru.itis.homework6.di.ServiceLocator
import ru.itis.homework6.ui.AuthorizationTextButton
import ru.itis.homework6.ui.PasswordTextField
import ru.itis.homework6.ui.RegistrationButton
import ru.itis.homework6.ui.Text
import ru.itis.homework6.ui.TextField
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
                    Text(text = getString(R.string.registration))
                    Spacer(modifier = Modifier.height(30.dp))
                    TextField(
                        value = username,
                        onValueChange = { newValue ->
                            username = newValue },
                        label = getString(R.string.username),
                        keyboardType = KeyboardType.Text
                    )
                    TextField(
                        value = email,
                        onValueChange = { newValue ->
                            email = newValue },
                        label = getString(R.string.email),
                        keyboardType = KeyboardType.Email
                    )
                    TextField(
                        value = phone ,
                        onValueChange = { newValue ->
                            phone = newValue },
                        label = getString(R.string.phone),
                        keyboardType = KeyboardType.Phone
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
        if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, context?.getString(R.string.fields_filled), Toast.LENGTH_SHORT).show()
            return
        }
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

                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, AuthorizationFragment())
                    .addToBackStack(null)
                    .commit()

                Toast.makeText(context, context?.getString(R.string.you_have_registered), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, context?.getString(R.string.user_exists), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun authorization() {
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