package com.example.coursebox.presentation.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.themoviesapp.presentation.theme.Caption

@Composable
fun AppTextFieldPlaceholder(
    text: String,
    color: Color = Caption,
    style: TextStyle = MaterialTheme.typography.bodyMedium
){
    Text(
        text = text,
        color = color,
        style = style
    )
}