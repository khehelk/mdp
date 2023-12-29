package com.example.myapplication.composeui.Orders

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.Service
import com.example.myapplication.ui.theme.TextPrimary

@Composable
fun OrderScrollService(item: Service){
    val maxWidth = (LocalConfiguration.current.screenWidthDp/2)
    Box(
        modifier = Modifier
            .padding(4.dp)
            .widthIn(max = maxWidth.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(145.dp)
                .background(color = Color.White, RoundedCornerShape(15.dp))
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ){
            Image(
                bitmap = item.photo.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .heightIn(min = 100.dp)
                    .padding(5.dp)
                    .widthIn(max = (maxWidth / 2).dp),
                contentScale = ContentScale.FillHeight,
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .widthIn(max = (maxWidth / 2).dp),
                verticalArrangement = Arrangement.Top,
            ){
                Text(text = item.name,
                    color = TextPrimary,
                    style = MaterialTheme.typography.bodyMedium)
                Text(text = item.price.toString(),
                    color = TextPrimary,
                    style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = "$${item.price}",
                    color = TextPrimary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}