package ru.itis.homework6.screens

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBindings
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.itis.homework6.R
import ru.itis.homework6.databinding.FragmentRegistrationBinding
import ru.itis.homework6.ui.AuthorizationTextButton
import ru.itis.homework6.ui.EmailTextField
import ru.itis.homework6.ui.LoginTextField
import ru.itis.homework6.ui.PasswordTextField
import ru.itis.homework6.ui.RegistrationButton
import ru.itis.homework6.ui.RegistrationText

class RegistrationFragment: BaseFragment(R.layout.fragment_registration) {

    private val viewBinding: FragmentRegistrationBinding by viewBinding(FragmentRegistrationBinding::bind)

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
                    LoginTextField()
                    EmailTextField()
                    PasswordTextField()
                    RegistrationButton(onClick = {})
                    AuthorizationTextButton(onClick = {})
                }
            }
        }
    }
}