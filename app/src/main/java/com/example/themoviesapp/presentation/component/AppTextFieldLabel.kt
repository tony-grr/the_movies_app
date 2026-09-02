package com.example.coursebox.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun AppTextFieldLabel(
    text: String ,
    modifier: Modifier = Modifier.padding(bottom = 8.dp),
    color: Color = MaterialTheme.colorScheme.secondary,
    style: TextStyle = MaterialTheme.typography.titleMedium,

){
    Text(
        text = text,
        color = color,
        style = style,
        modifier = modifier
    )
}