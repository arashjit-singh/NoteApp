package com.pkg.noteapp.presentation.noteList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pkg.noteapp.R
import com.pkg.noteapp.presentation.noteList.components.NoteListHeader
import com.pkg.noteapp.presentation.noteList.components.NoteView

@Composable
fun NoteListScreen(
    viewModel: NoteListViewModel = hiltViewModel(),
    onGotoDetailAction: (noteId: Int?) -> Unit,
) {

    val state = viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            NoteListHeader()
            LazyColumn() {
                items(state.value.list) {
                    NoteView(note = it, onNoteClick = {
                        onGotoDetailAction(it.id)
                    })
                }
            }
        }

        FloatingActionButton(
            onClick = { onGotoDetailAction(-1) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription = "Add Note")
        }
    }


}

@Composable
@Preview
fun PreviewUI() {
    NoteListScreen(onGotoDetailAction = {})
}

