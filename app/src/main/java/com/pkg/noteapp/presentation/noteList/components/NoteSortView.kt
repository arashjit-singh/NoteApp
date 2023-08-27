package com.pkg.noteapp.presentation.noteList.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.pkg.noteapp.R
import com.pkg.noteapp.domain.SortBy
import com.pkg.noteapp.domain.SortOrder

@Composable
fun NoteSortView(
    modifier: Modifier = Modifier,
    onUpdateSortBy: (SortBy) -> Unit,
    onUpdateSortOrder: (SortOrder) -> Unit,
    sortBy: SortBy?,
    sortOrder: SortOrder?,
) {

    var sortByTitle by rememberSaveable {
        mutableStateOf(false)
    }

    var sortByDate by rememberSaveable {
        mutableStateOf(false)
    }

    var sortByColor by rememberSaveable {
        mutableStateOf(false)
    }

    var sortOrderAsc by rememberSaveable {
        mutableStateOf(false)
    }

    var sortOrderDesc by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = sortBy) {
        sortBy?.let {
            when (it) {
                SortBy.Color -> {
                    sortByColor = true
                    sortByTitle = false
                    sortByDate = false
                }

                SortBy.Date -> {
                    sortByColor = false
                    sortByTitle = false
                    sortByDate = true
                }

                SortBy.Title -> {
                    sortByColor = false
                    sortByTitle = true
                    sortByDate = false
                }
            }
        }
    }

    LaunchedEffect(key1 = sortOrder) {
        sortOrder?.let {
            when (it) {
                SortOrder.Ascending -> {
                    sortOrderDesc = false
                    sortOrderAsc = true
                }

                SortOrder.Descending -> {
                    sortOrderAsc = false
                    sortOrderDesc = true
                }
            }
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = sortByTitle, onClick = {
                onUpdateSortBy(SortBy.Title)
            })
            Text(text = stringResource(R.string.title), modifier = Modifier.clickable {
                onUpdateSortBy(SortBy.Title)
            })
            RadioButton(selected = sortByDate, onClick = {
                onUpdateSortBy(SortBy.Date)
            })
            Text(text = stringResource(R.string.date), modifier = Modifier.clickable {
                onUpdateSortBy(SortBy.Date)
            })
            RadioButton(selected = sortByColor, onClick = {
                onUpdateSortBy(SortBy.Color)
            })
            Text(text = stringResource(R.string.color), modifier = Modifier.clickable {
                onUpdateSortBy(SortBy.Color)
            })
        }
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = sortOrderAsc, onClick = {
                onUpdateSortOrder(SortOrder.Ascending)
            })
            Text(text = stringResource(R.string.ascending), modifier = Modifier.clickable {
                onUpdateSortOrder(SortOrder.Ascending)
            })
            RadioButton(selected = sortOrderDesc, onClick = {
                onUpdateSortOrder(SortOrder.Descending)
            })
            Text(text = stringResource(R.string.descending), modifier = Modifier.clickable {
                onUpdateSortOrder(SortOrder.Descending)
            })
        }
    }
}

@Composable
@Preview
fun PreviewNoteSortView() {
    NoteSortView(
        onUpdateSortBy = {},
        onUpdateSortOrder = {},
        sortOrder = SortOrder.Ascending,
        sortBy = SortBy.Date
    )
}