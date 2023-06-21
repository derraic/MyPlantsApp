package com.derra.myplantsapp.add_edit_plant

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    value: String,
    maxLines: Int,
    onValueChange: () -> Unit

) {

    Box(
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(width = 1.dp, color = MaterialTheme.colors.primary, shape = RoundedCornerShape(8.dp))
    ) {
        TextField(modifier = modifier, value = value, onValueChange = {
            onValueChange()
        }, maxLines = maxLines)
    }
}