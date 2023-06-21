package com.derra.myplantsapp.add_edit_plant

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel


@Composable
fun SpinnerField(
    modifier: Modifier,
    values: List<String>,
    value: String,
    onEvent: () -> Unit
) {

    val expanded = remember { mutableStateOf(false) }
    Box (
        modifier = modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { expanded.value = true },

    ) {

        TextField(
            value = value,
            onValueChange = { onEvent},
            textStyle = MaterialTheme.typography.body1,
            readOnly = true,

        )
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },

        ) {
            values.forEach { item ->
                Box(modifier = Modifier.clickable {onEvent}) {
                    Text(
                        text = item,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),

                        )
                }
            }
        }
    }
}