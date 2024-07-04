package com.example.darckoum.screen.common

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedTextFieldSample(
    label: String,
    modifier: Modifier,
    text: MutableState<String>,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    minLines: Int = 1,
    maxLines: Int = 1
) {
    OutlinedTextField(
        value = text.value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = { Text(text = label) },
        colors = OutlinedTextFieldDefaults.colors(),
        singleLine = true,
        shape = RoundedCornerShape(14.dp),
        keyboardOptions = keyboardOptions
    )
}