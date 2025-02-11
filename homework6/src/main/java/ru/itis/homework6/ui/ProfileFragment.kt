package ru.itis.homework6.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import ru.itis.homework6.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.itis.homework6.data.db.entities.UserEntity
import ru.itis.homework6.data.db.repository.UserRepository
import androidx.compose.material3.ExtendedFloatingActionButton as ExtendedFloatingActionButton1

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDemo(
    username: String,
    email: String,
    phone: String,
    password: String,
    onSave: (String, String, String, String) -> Unit
) {
    var newUsername by remember { mutableStateOf(username) }
    var newEmail by remember { mutableStateOf(email) }
    var newPhone by remember { mutableStateOf(phone) }
    var newPassword by remember { mutableStateOf(password) }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton1(
                text = { Text(text = stringResource(id = R.string.edit_profile)) },
                icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                onClick = {
                    showBottomSheet = true
                }
            )
        }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {}
        if (showBottomSheet) {
            ModalBottomSheet (
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                Text(text = stringResource(id = R.string.edit_profile),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Thin,
                    modifier = Modifier.padding(start = 16.dp))

                OutlinedTextField(
                    value = newUsername,
                    onValueChange = { newUsername = it },
                    label = { Text(text = stringResource(id = R.string.username)) },
                    modifier = Modifier.padding(start = 16.dp, top = 10.dp, end = 16.dp)
                        .fillMaxWidth(1f)
                )
                OutlinedTextField(
                    value = newEmail,
                    onValueChange = { newEmail = it },
                    label = { Text(text = stringResource(id = R.string.email)) },
                    modifier = Modifier.padding(start = 16.dp, top = 10.dp, end = 16.dp)
                        .fillMaxWidth(1f)
                )
                OutlinedTextField(
                    value = newPhone,
                    onValueChange = { newPhone = it },
                    label = { Text(text = stringResource(id = R.string.phone)) },
                    modifier = Modifier.padding(start = 16.dp, top = 10.dp, end = 16.dp)
                        .fillMaxWidth(1f)
                )
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = { Text(text = stringResource(id = R.string.password)) },
                    modifier = Modifier.padding(start = 16.dp, top = 10.dp, end = 16.dp)
                        .fillMaxWidth(1f)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    FilledTonalButton(shape = RoundedCornerShape(15.dp),
                        onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }
                    },
                    modifier = Modifier.padding(top = 10.dp, start = 16.dp))
                    {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    FilledTonalButton(shape = RoundedCornerShape(15.dp),
                        onClick = {
                            onSave(newUsername, newEmail, newPhone, newPassword)
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                }
                            }
                        },
                        modifier = Modifier.padding(top = 10.dp, end = 16.dp))
                    {
                        Text(text = stringResource(id = R.string.save))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ProfileText() {
    Text(text = stringResource(id = R.string.profile), fontWeight = FontWeight.Thin,
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(top = 100.dp),
        fontSize = 28.sp,
        textAlign = TextAlign.Center)
}


@Preview
@Composable
fun ProfileButton(onClick: () -> Unit, text: String) {
    FilledTonalButton(
        onClick = { onClick() },
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.padding(top = 70.dp, start = 25.dp)
    ) {
        Text(text)
    }
}

@Preview
@Composable
fun ProfileScreen(userState: UserEntity?) {
    Column(modifier = Modifier.padding(top = 50.dp, start = 25.dp)) {
        userState?.let {
            Text(text = "Username: ${it.username}",
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(top = 15.dp, start = 25.dp),
                textAlign = TextAlign.Left)
            Text(text = "Email: ${it.email}",
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(top = 15.dp, start = 25.dp),
                textAlign = TextAlign.Left)
            Text(text = "Phone: ${it.phone}",
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(top = 15.dp, start = 25.dp),
                textAlign = TextAlign.Left)
            Text(text = "Password: ${it.password}",
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(top = 15.dp, start = 25.dp),
                textAlign = TextAlign.Left)
        } ?: run {
            Text(text = stringResource(id = R.string.user_not_found))
        }
    }
}

