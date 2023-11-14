package com.example.myapplication.composeui.List_of_Services

import android.content.ContentResolver
import android.graphics.BitmapFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.composeui.UIComponents.MyTextField
import com.example.myapplication.ui.theme.BlueMain
import com.example.myapplication.ui.theme.GreenBtn
import com.example.myapplication.ui.theme.TextPrimary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddService (navController: NavController){
    var serviceName = ""
    var price = ""
    var expanded by remember { mutableStateOf(false) }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(BlueMain)
            .padding(15.dp)
            .padding(bottom = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Box(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 10.dp)
                .height(150.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(15.dp),
                    clip = false
                ),
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(145.dp)
                    .background(color = Color.White, RoundedCornerShape(15.dp))
                    .padding(15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ){
               /* Image(
                    bitmap = selectedImage!!,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight()
                        .heightIn(min = 100.dp)
                        .widthIn(max = (LocalConfiguration.current.screenWidthDp / 3).dp),
                    contentScale = ContentScale.FillHeight,
                )*/

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .widthIn(max = (LocalConfiguration.current.screenWidthDp / 3).dp),
                    verticalArrangement = Arrangement.Top,
                ){
                    serviceName?.let {
                        Text(
                            text = it,
                            color = TextPrimary,
                            style = MaterialTheme.typography.bodyMedium)
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .widthIn(max = (LocalConfiguration.current.screenWidthDp / 3).dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ){
                    Text(
                        text = "${price}$",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
        Column (
        ){
            Row (modifier = Modifier.padding(vertical = 5.dp)){
                MyTextField(label = "Service name"){
                    newValue ->
                    serviceName = newValue
                }
            }
            Row (modifier = Modifier.padding(vertical = 5.dp)){
                MyTextField(label = "Price"){
                    newValue ->
                    price = newValue
                }
            }
        }
        Button(
            onClick = {
//                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//                launcher.launch(intent)
            },
            modifier = Modifier
                .height(60.dp)
                .padding(top = 10.dp)
                .fillMaxWidth()
                .clip(CircleShape),
            colors = ButtonDefaults.buttonColors(
                containerColor = GreenBtn,
                contentColor = Color.White
            ),
            contentPadding = PaddingValues(0.dp),
        ) {
            Text(text = "Upload image", style = MaterialTheme.typography.bodyMedium.copy(Color.White))
        }
        Button(
            onClick = {

            },
            modifier = Modifier
                .height(60.dp)
                .padding(top = 10.dp)
                .fillMaxWidth()
                .clip(CircleShape),
            colors = ButtonDefaults.buttonColors(
                containerColor = GreenBtn,
                contentColor = Color.White
            ),
            contentPadding = PaddingValues(0.dp),
        ) {
            Text(text = "Add service", style = MaterialTheme.typography.bodyMedium.copy(Color.White))
        }
    }
}

private suspend fun loadSelectedImage(uri: android.net.Uri, contentResolver: ContentResolver): ImageBitmap {
    return withContext(Dispatchers.IO) {
        val inputStream = contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        bitmap.asImageBitmap()
    }
}