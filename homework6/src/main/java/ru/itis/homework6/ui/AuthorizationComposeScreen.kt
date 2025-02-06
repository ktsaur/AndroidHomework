package ru.itis.homework6.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun AuthorisationText() {
    Text(text = "Authorization", fontWeight = FontWeight.Thin,
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(top = 100.dp),
        fontSize = 28.sp,
        textAlign = TextAlign.Center)
}

@Preview
@Composable
fun LoginTextField() {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(text = "Login") },
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 70.dp)
            .width(250.dp)
    )
}

@Preview
@Composable
fun PasswordTextField() {
    var password by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text(text = "Password") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 20.dp)
            .width(250.dp)
    )
}

@Preview
@Composable
fun LoginButton(onClick: () -> Unit) {
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
fun RegistrationTextButton(onClick: () -> Unit) {
    TextButton(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(top = 5.dp)
    ) {
        Text(text = "Register",
            fontWeight = FontWeight.Thin,
            textAlign = TextAlign.Center,
            color = Color.Black)
    }
}