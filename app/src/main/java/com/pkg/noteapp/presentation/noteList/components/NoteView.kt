package com.pkg.noteapp.presentation.noteList.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pkg.noteapp.R
import com.pkg.noteapp.domain.Note
import com.pkg.noteapp.util.ColorResource

@Composable
fun NoteView(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteClick: (note: Note) -> Unit,
    onDeleteNode: (note: Note) -> Unit,
) {
    Card(modifier = modifier
        .fillMaxWidth()
        .clickable {
            onNoteClick(note)
        }
        .padding(top = 4.dp, bottom = 4.dp, start = 7.dp, end = 7.dp)
    ) {
        Row(
            modifier = Modifier.background(color = Color(note.color.value)),
            verticalAlignment = Alignment.Bottom
        ) {
            Column(
                modifier = Modifier
                    .padding(7.dp)
                    .weight(1.0f)
            ) {
                Text(
                    text = note.title,
                    style = TextStyle(
                        fontSize = 24.sp, fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.padding(7.dp)
                )
                Text(
                    text = note.description ?: "",
                    style = TextStyle(
                        fontSize = 20.sp, fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier.padding(7.dp),
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Box {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = stringResource(R.string.content_dec_delete_note),
                    modifier = Modifier
                        .clickable {
                            onDeleteNode(note)
                        }
                        .size(50.dp)
                        .align(Alignment.BottomEnd)
                        .padding(10.dp)
                )
            }
        }
    }
}

@Composable
@Preview
fun NotePreView() {
    NoteView(note = Note(
        id = null,
        title = "Lorem Ipsum",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. ",
        dateTime = 10L,
        color = ColorResource.Yellow
    ), onNoteClick = {

    }, onDeleteNode = {})
}