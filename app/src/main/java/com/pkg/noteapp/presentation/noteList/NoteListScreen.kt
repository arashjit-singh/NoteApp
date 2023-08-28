package com.pkg.noteapp.presentation.noteList

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pkg.noteapp.R
import com.pkg.noteapp.presentation.noteList.components.NoteListHeader
import com.pkg.noteapp.presentation.noteList.components.NoteView
import kotlinx.coroutines.launch

@Composable
fun NoteListScreen(
    viewModel: NoteListViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    onGotoDetailAction: (noteId: Int?) -> Unit,
) {

    val state = viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val listState = rememberLazyListState()

    LaunchedEffect(key1 = state.value.message) {
        state.value.message?.let {
            val resp = snackbarHostState.showSnackbar(
                message = it, actionLabel = context.getString(R.string.undo)
            )
            when (resp) {
                SnackbarResult.Dismissed -> {
                    Log.i("TAG", "Dismissed")
                }

                SnackbarResult.ActionPerformed -> {
                    viewModel.undoNoteDelete()
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            NoteListHeader(onUpdateSortBy = {
                viewModel.updateSortBy(it)
                scope.launch {
                    listState.scrollToItem(0)
                }
            }, onUpdateSortOrder = {
                viewModel.updateSortOrder(it)
                scope.launch {
                    listState.scrollToItem(0)
                }
            }, sortBy = state.value.sortBy, sortOrder = state.value.sortOrder
            )
            LazyColumn(state = listState) {
                items(state.value.list) {
                    NoteView(note = it, onNoteClick = {
                        onGotoDetailAction(it.id)
                    }, onDeleteNode = {
                        viewModel.deleteNote(it)
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
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = stringResource(
                    R.string.add_note
                )
            )
        }
    }
}

