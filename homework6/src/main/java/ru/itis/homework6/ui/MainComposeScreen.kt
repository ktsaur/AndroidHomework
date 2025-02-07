package ru.itis.homework6.ui

import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.FilledTonalButton

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itis.homework6.data.db.entities.SongEntity

@Preview
@Composable
fun MainText() {
    Text(text = "List songs", fontWeight = FontWeight.Thin,
        modifier = Modifier
            .padding(top = 28.dp, start = 16.dp),
        fontSize = 28.sp,
        textAlign = TextAlign.Left)
}

@Preview
@Composable
fun AddSongButton(onClick: () -> Unit) {
    FilledTonalButton(
        onClick = { onClick() },
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.padding(top = 25.dp, end = 16.dp)
    ) {
        Text("Add song")
    }
}

@Preview
@Composable
fun ProfileButton(onClick: () -> Unit) {
    FilledTonalButton(
        onClick = { onClick() },
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.padding(top = 25.dp)
    ) {
        Text("Profile")
    }
}

@Preview
@Composable
fun SongsList(
    listState: LazyListState,
    items: List<SongEntity>
) {
    LazyColumn(state = listState, modifier = Modifier.padding(top = 30.dp)) {
        items(items.size) { position ->
            Column{
                Divider()
                ItemSong(model = items[position])
            }
        }
    }
}

@Preview
@Composable
fun ItemSong(model: SongEntity) {
    Column {
        Text(
            text = model.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp)
        )
        Text(
            text = model.author,
            fontSize = 14.sp,
            fontWeight = FontWeight.Thin,
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp)
        )
    }
}



