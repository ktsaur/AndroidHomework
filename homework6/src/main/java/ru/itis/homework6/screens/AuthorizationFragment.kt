package ru.itis.homework6.screens

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.itis.homework6.R
import ru.itis.homework6.databinding.FragmentAuthorisationBinding
import ru.itis.homework6.ui.AuthorisationText
import ru.itis.homework6.ui.LoginButton
import ru.itis.homework6.ui.LoginTextField
import ru.itis.homework6.ui.PasswordTextField
import ru.itis.homework6.ui.RegistrationTextButton

class AuthorizationFragment:BaseFragment(R.layout.fragment_authorisation) {

    private val viewBinding: FragmentAuthorisationBinding by viewBinding(FragmentAuthorisationBinding::bind)

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
                    LoginTextField()
                    PasswordTextField()
                    LoginButton(onClick = {login()})
                    RegistrationTextButton (onClick = {registration()})
                }
            }
        }
    }

    fun login() { //тут надо проверить, если пользователь есть в системе, то отправляем на главную страницу, если нет то выходит ошибка

        parentFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment())
            .addToBackStack(null)
            .commit()

    }

    fun registration() {

        parentFragmentManager.beginTransaction()
            .replace(R.id.container, RegistrationFragment())
            .addToBackStack(null)
            .commit()

    }
}