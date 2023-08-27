package com.pkg.noteapp.presentation.noteDetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pkg.noteapp.util.ColorResource

@Composable
fun ColorPickerView(
    modifier: Modifier = Modifier,
    currentValue: ColorResource,
    onColorSelected: (ColorResource) -> Unit,
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ColorView(ColorResource.Blue, currentValue) { onColorSelected(it) }
        ColorView(ColorResource.Yellow, currentValue) { onColorSelected(it) }
        ColorView(ColorResource.Magenta, currentValue) { onColorSelected(it) }
        ColorView(ColorResource.Cyan, currentValue) { onColorSelected(it) }
        ColorView(ColorResource.White, currentValue) { onColorSelected(it) }
    }
}

@Composable
fun ColorView(
    color: ColorResource,
    currentValue: ColorResource,
    onColorSelected: (ColorResource) -> Unit,
) {
    Text(
        text = "", modifier = Modifier
            .background(
                shape = RoundedCornerShape(40.dp),
                color = Color(color.value)
            )
            .border(
                width = 1.dp,
                color = if (currentValue == color) Color.Black else Color.Transparent
            )
            .clipToBounds()
            .size(40.dp)
            .clip(RoundedCornerShape(40.dp))
            .clipToBounds()
            .clickable {
                onColorSelected(color)
            }
    )
}

@Composable
@Preview
fun PreviewColorPickerView() {
    ColorPickerView(onColorSelected = {}, currentValue = ColorResource.Yellow)
}
