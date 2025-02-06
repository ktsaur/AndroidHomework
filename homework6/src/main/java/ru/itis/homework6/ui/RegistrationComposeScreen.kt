package ru.itis.homework6.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun RegistrationText() {
    Text(text = "Registration", fontWeight = FontWeight.Thin,
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(top = 100.dp),
        fontSize = 28.sp,
        textAlign = TextAlign.Center)
}

@Preview
@Composable
fun EmailTextField() {
    var email by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text(text = "Email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 20.dp)
            .width(250.dp)
    )
}

@Preview
@Composable
fun RegistrationButton(onClick: () -> Unit) {
    FilledTonalButton(
        onClick = { onClick() },
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.padding(top = 70.dp)
    ) {
        Text("Login")
    }
}

@Preview
@Composable
fun AuthorizationTextButton(onClick: () -> Unit) {
    TextButton(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(top = 5.dp)
    ) {
        Text(text = "Зарегистрироваться",
            fontWeight = FontWeight.Thin,
            textAlign = TextAlign.Center,
            color = Color.Black)
    }
}