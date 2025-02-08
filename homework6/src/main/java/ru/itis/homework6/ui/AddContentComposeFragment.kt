package ru.itis.homework6.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun AddContentText() {
    Text(text = "Add new song", fontWeight = FontWeight.Thin,
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(top = 100.dp),
        fontSize = 28.sp,
        textAlign = TextAlign.Center)
}

@Preview
@Composable
fun TitleTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    var text by remember { mutableStateOf(value) }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onValueChange(it)},
        label = { Text(text = "Title") },
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 70.dp)
            .width(250.dp)
    )
}
@Preview
@Composable
fun SingerTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    var text by remember { mutableStateOf(value) }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onValueChange(it)},
        label = { Text(text = "Singer") },
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 20.dp)
            .width(250.dp)
    )
}
@Preview
@Composable
fun AuthorTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    var text by remember { mutableStateOf(value) }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onValueChange(it)},
        label = { Text(text = "Author") },
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 20.dp)
            .width(250.dp)
    )
}


@Preview
@Composable
fun GenreTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    var text by remember { mutableStateOf(value) }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onValueChange(it)},
        label = { Text(text = "Genre") },
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 20.dp)
            .width(250.dp)
    )
}

@Preview
@Composable
fun AddNewSongButton(onClick: () -> Unit) {
    FilledTonalButton(
        onClick = { onClick() },
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.padding(top = 50.dp)
    ) {
        Text("Add new song")
    }
}

