package com.pkg.noteapp.presentation.noteDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pkg.noteapp.R
import com.pkg.noteapp.presentation.noteDetail.components.ColorPickerView

@Composable
fun NoteDetailScreen(viewModel: NoteDetailsViewModel = hiltViewModel()) {

    val state = viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(state.value.color.value))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {

            ColorPickerView(onColorSelected = {
                viewModel.updateBgColor(it)
            })

            BasicTextField(
                value = state.value.title ?: "Enter Title...", onValueChange = {
                    viewModel.updateTitle(it)
                }, singleLine = true, textStyle = TextStyle(
                    fontSize = 24.sp, color = MaterialTheme.colorScheme.onBackground
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            BasicTextField(
                value = state.value.description ?: "Enter Description...", onValueChange = {
                    viewModel.updateDescription(it)
                }, singleLine = false, textStyle = TextStyle(
                    fontSize = 24.sp, color = MaterialTheme.colorScheme.onBackground
                )
            )
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