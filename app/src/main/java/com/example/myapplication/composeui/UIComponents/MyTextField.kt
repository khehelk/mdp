package com.example.myapplication.composeui.UIComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.BlueBorder
import com.example.myapplication.ui.theme.TextSecondary

@Composable
fun MyTextField (
    label: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChanged: (String) -> Unit,
){
    val textState = remember { mutableStateOf(TextFieldValue()) }
    val text by rememberUpdatedState(newValue = textState.value)
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
        .clip(RoundedCornerShape(15.dp))
        .fillMaxWidth()
        .background(Color.White)
        .border(2.dp, color = BlueBorder, RoundedCornerShape(15.dp))
        .height(45.dp)
        .padding(horizontal = 15.dp),
    ){
        if(textState.value.text.isEmpty()){
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary),
            )
        }
        BasicTextField(
            value = text,
            onValueChange = {
                textState.value = it
                onValueChanged(it.text)
            },
            visualTransformation = visualTransformation,
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyMedium,
            singleLine = true,
        )
    }
}