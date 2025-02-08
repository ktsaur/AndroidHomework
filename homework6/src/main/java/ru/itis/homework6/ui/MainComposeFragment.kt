package ru.itis.homework6.ui

import androidx.compose.foundation.background
import ru.itis.homework6.R
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itis.homework6.data.db.entities.SongEntity

@Preview
@Composable
fun MainText() {
    Text(text = stringResource(id = R.string.list_songs), fontWeight = FontWeight.Thin,
        modifier = Modifier
            .padding(top = 28.dp, start = 16.dp),
        fontSize = 28.sp,
        textAlign = TextAlign.Left)
}

@Preview
@Composable
fun Button(onClick: () -> Unit, text: String) {
    FilledTonalButton(
        onClick = { onClick() },
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.padding(top = 25.dp)
    ) {
        Text(text)
    }
}


@Preview
@Composable
fun LogoutButton(onClick: () -> Unit) {
    FilledTonalButton(
        onClick = { onClick() },
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.padding(top = 25.dp)
    ) {
        Text(text = stringResource(id = R.string.logout))
    }
}

@Preview
@Composable
fun SongsList(
    listState: LazyListState,
    items: List<SongEntity>,
    onSongLongPress: (SongEntity) -> Unit
) {
    LazyColumn(state = listState, modifier = Modifier.padding(top = 30.dp)) {
        items(items.size) { position ->
            val song = items[position]
            Column{
                ItemSong(
                    model = song,
                    onLongPress = {onSongLongPress(song)})
            }
        }
    }
}

@Preview
@Composable
fun ItemSong(
    model: SongEntity,
    onLongPress: () -> Unit
) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { onLongPress() }
                )
            }
    ){
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = model.title, fontWeight = FontWeight.Bold)
            Text(text = model.singer)
        }
    }
}
