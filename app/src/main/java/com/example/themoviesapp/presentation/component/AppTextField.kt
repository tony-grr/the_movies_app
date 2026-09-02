package com.example.coursebox.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.secondary),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource? = null,
    cursorBrush: Brush = SolidColor(MaterialTheme.colorScheme.secondary),
    label:@Composable (()->Unit)? = null,
    placeholder:@Composable (()->Unit)? = null
){
    Column {
        label?.invoke()
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle,
            cursorBrush = cursorBrush,
            modifier = modifier,
            enabled = enabled,
            readOnly = readOnly,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            maxLines = maxLines,
            minLines = minLines,
            visualTransformation = visualTransformation,
            onTextLayout = onTextLayout,
            interactionSource = interactionSource
        ) {innerTextField->
            Box(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(30.dp))
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .defaultMinSize(minWidth = 280.dp, minHeight = 40.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                innerTextField()
                if (value.isEmpty()){
                    placeholder?.invoke()
                }
            }
        }
    }

}