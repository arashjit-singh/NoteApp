package com.pkg.noteapp.presentation.noteDetail

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pkg.noteapp.R
import com.pkg.noteapp.presentation.noteDetail.components.ColorPickerView

@Composable
fun NoteDetailScreen(
    viewModel: NoteDetailsViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
) {

    val state = viewModel.uiState.collectAsState()

    val animatedColor by animateColorAsState(
        Color(state.value.color.value)
    )

    LaunchedEffect(key1 = state.value.message) {
        state.value.message?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.snackBarShown()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = animatedColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            ColorPickerView(currentValue = state.value.color, onColorSelected = {
                viewModel.updateBgColor(it)
            })

            TextField(value = state.value.title ?: "", onValueChange = {
                viewModel.updateTitle(it)
            }, modifier = Modifier.fillMaxWidth(), colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ), textStyle = TextStyle(
                fontSize = 24.sp
            ), label = { Text(text = stringResource(R.string.label_title)) })

            Spacer(modifier = Modifier.height(10.dp))

            TextField(value = state.value.description ?: "", onValueChange = {
                viewModel.updateDescription(it)
            }, modifier = Modifier.fillMaxWidth(), colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ), textStyle = TextStyle(
                fontSize = 24.sp
            ), label = { Text(text = stringResource(R.string.description)) })
        }

        FloatingActionButton(
            onClick = { viewModel.insertOrUpdateNote() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_save), contentDescription = "Save Note"
            )
        }
    }
}