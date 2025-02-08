package ru.itis.homework6.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.Card
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
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
fun LogoutButton(onClick: () -> Unit) {
    FilledTonalButton(
        onClick = { onClick() },
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.padding(top = 25.dp)
    ) {
        Text("Log out")
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
                ItemSong(model = items[position])
            }
        }
    }
}

@Preview
@Composable
fun ItemSong(model: SongEntity) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White)
    ){
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = model.title, fontWeight = FontWeight.Bold)
            Text(text = model.singer)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDemo(
    onDelete: () -> Unit,
    onLogOut: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("") },
                backgroundColor = Color.LightGray,
                icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                onClick = {
                    showBottomSheet = true
                }
            )
        }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {}

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                // Sheet content
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    FilledTonalButton(shape = RoundedCornerShape(15.dp),
                        onClick = { onDelete() },
                        modifier = Modifier.padding(top = 10.dp, start = 16.dp))
                    { Text("Delete account") }
                    FilledTonalButton(shape = RoundedCornerShape(15.dp),
                        onClick = { onLogOut() },
                        modifier = Modifier.padding(top = 10.dp, end = 16.dp)
                    ) { Text("Log out") }
                }
            }
        }
    }
}



