package ru.itis.homework5.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itis.homework5.R
import ru.itis.homework5.model.AnswerModel
import kotlin.math.exp


@Preview
@Composable
fun SimpleOutlinedTextFieldSample() {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(
                text = stringResource(id = R.string.number_of_coroutines),
            )},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 70.dp)
            .width(360.dp)
    )
}


@Preview
@Composable
fun RadioButtonSingleSelection(modifier: Modifier = Modifier) {
    val radioOptions = listOf("Sequentially", "Parallel")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
    Column(modifier.selectableGroup()) {
        Text (
            text = "How will coroutines be launched?",
            color = colorResource(id = R.color.black),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 30.dp)
                .width(360.dp)
                .wrapContentSize(Alignment.Center)
        )
        Divider(
            color = Color.White,
            thickness = 15.dp
        )
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption), //Указывает, выбран ли переключатель.
                    onClick = null // null recommended for accessibility with screen readers
                // лямбда-функция, которая выполняется при нажатии переключателя.
                // Если это null , пользователь не может напрямую взаимодействовать с переключателем.
                )
                Text(
                    text = text,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun RadioButtonLogicalSelection(modifier: Modifier = Modifier) {
    val radioOptions = listOf("Cancel", "Continue")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
    Column(modifier.selectableGroup()) {
        Text (
            text = "What is the logic behind how coroutines work?",
            color = colorResource(id = R.color.black),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 30.dp)
                .width(360.dp)
                .wrapContentSize(Alignment.Center)
        )
        Divider(
            color = Color.White,
            thickness = 15.dp
        )
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption), //Указывает, выбран ли переключатель.
                    onClick = null // null recommended for accessibility with screen readers
                    // лямбда-функция, которая выполняется при нажатии переключателя.
                    // Если это null , пользователь не может напрямую взаимодействовать с переключателем.
                )
                Text(
                    text = text,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun MainScreen2() {
    Surface(modifier = Modifier
        .padding(start = 100.dp, end = 100.dp, top = 30.dp)
        .width(200.dp)
        .wrapContentSize(Alignment.Center)
    ) {
        val list = listOf("Default", "Main", "Unconfined", "IO")
        val currentValue = remember { mutableStateOf(list[0]) }
        val expanded = remember { mutableStateOf(false) }

        Box(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier
                .clickable {
                    expanded.value = !expanded.value
                }
                .align(Alignment.Center)) {

                Text(
                    text = currentValue.value,
                    style = TextStyle(color = Color.Black, fontSize = 20.sp)
                )
                Divider(
                    color = Color.White,
                    thickness = 10.dp,
                    modifier = Modifier.width(10.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_keyboard),
                    contentDescription = null)

                DropdownMenu(expanded = expanded.value, onDismissRequest = {
                    expanded.value = !expanded.value
                }) {

                    list.forEach {
                        DropdownMenuItem(onClick = {
                            expanded.value = false
                            currentValue.value = it

                        }) {
                            Text(text = it)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LaunchButtonExample() {
    OutlinedButton(onClick = {  },
        modifier = Modifier.size(width = 170.dp , height = 40.dp)) {
        Text("Launch")
    }
}

@Composable
fun CancelButtonExample() { //здесь надо передать функцию onBottonClick()
    OutlinedButton(onClick = { },
        modifier = Modifier.size(width = 170.dp , height = 40.dp)) {
        Text("Cancel")
    }
}


@Composable
fun ComposeListSample(
    listState: LazyListState,
    items: List<AnswerModel>
) {
    LazyColumn (state = listState) {
        item {
            Text(
                text = "List beginning",
                fontSize = 32.sp,
                modifier = Modifier
                    .padding(16.dp)
            )
        }
        items(items.size) { position ->
            Column {
                ListItemSample(model = items[position])
                Divider()
            }
        }
    }
}


//здесь отрисовываем элемент списка
@Composable
fun ListItemSample(model: AnswerModel ) {
    Row {
        Text(
            text = model.answerText,
            modifier = Modifier.size(80.dp))
        Text(
            text = model.answerId,
            modifier = Modifier.size(80.dp))
    }
}



