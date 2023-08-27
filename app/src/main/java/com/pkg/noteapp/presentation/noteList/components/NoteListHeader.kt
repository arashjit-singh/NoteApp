package com.pkg.noteapp.presentation.noteList.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pkg.noteapp.R
import com.pkg.noteapp.domain.SortBy
import com.pkg.noteapp.domain.SortOrder

@Composable
fun NoteListHeader(
    modifier: Modifier = Modifier,
    onUpdateSortBy: (SortBy) -> Unit,
    onUpdateSortOrder: (SortOrder) -> Unit,
    sortBy: SortBy?,
    sortOrder: SortOrder?,
) {

    var showNoteSortView by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.your_notes),
                style = TextStyle(
                    fontStyle = FontStyle.Normal,
                    fontSize = 30.sp
                ),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1.0f)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_sort),
                contentDescription = stringResource(R.string.sort_list),
                modifier = Modifier
                    .clickable {
                        showNoteSortView = !showNoteSortView
                    }
                    .size(40.dp),
                colorFilter =
                ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
            )
        }

        if (showNoteSortView) {
            AnimatedVisibility(visible = showNoteSortView) {
                NoteSortView(onUpdateSortBy = {
                    onUpdateSortBy(it)
                }, onUpdateSortOrder = {
                    onUpdateSortOrder(it)
                },
                    sortBy = sortBy,
                    sortOrder = sortOrder
                )
            }
        }
    }


}

@Composable
@Preview
fun PreviewNoteListHeader() {
    NoteListHeader(
        onUpdateSortBy = {},
        onUpdateSortOrder = {},
        sortOrder = SortOrder.Ascending,
        sortBy = SortBy.Date
    )
}