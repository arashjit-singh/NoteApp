package com.pkg.noteapp.presentation.noteList.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NoteSortView(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = false, onClick = {})
            Text(text = "Title")
            RadioButton(selected = false, onClick = {})
            Text(text = "Date")
            RadioButton(selected = false, onClick = {})
            Text(text = "Color")
        }
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = false, onClick = {})
            Text(text = "Ascending")
            RadioButton(selected = false, onClick = {})
            Text(text = "Descending")
        }
    }
}

@Composable
@Preview
fun PreviewNoteSortView() {
    NoteSortView()
}