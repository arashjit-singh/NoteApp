package com.pkg.noteapp.presentation.noteList.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pkg.noteapp.R
import com.pkg.noteapp.domain.Note

@Composable
fun NoteView(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteClick: (note: Note) -> Unit,
    onDeleteNode:(note:Note) -> Unit
) {

    Column() {
        Row() {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        onNoteClick(note)
                    }
            ) {
                Box() {
                    Column() {
                        Text(
                            text = note.title, style = TextStyle(
                                fontSize = 24.sp, fontWeight = FontWeight.SemiBold
                            ), modifier = Modifier.padding(7.dp)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = note.description ?: "", style = TextStyle(
                                fontSize = 20.sp, fontWeight = FontWeight.Normal
                            ), modifier = Modifier.padding(7.dp)
                        )
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = "Delete note",
                        modifier =
                        Modifier.align(Alignment.BottomEnd).size(40.dp).clickable {
                            onDeleteNode(note)
                        }
                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(10.dp))
    }


}

@Composable
@Preview
fun NotePreView() {
    NoteView(
        note = Note(
            id = null,
            title = "Lorem Ipsum",
            description = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English.",
            dateTime = 10L,
            color = 0
        ), onNoteClick = {

        }, onDeleteNode = {}
    )
}