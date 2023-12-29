package com.example.myapplication.composeui.List_of_Services

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.api.ApiStatus
import com.example.myapplication.businessLogic.viewmodel.AppViewModelProvider
import com.example.myapplication.businessLogic.viewmodel.ServiceViewModel
import com.example.myapplication.composeui.NetworkUI.Loading
import com.example.myapplication.model.Service
import com.example.myapplication.ui.theme.BlueBorder
import com.example.myapplication.ui.theme.BlueMain
import com.example.myapplication.ui.theme.GreenBtn
import com.example.myapplication.ui.theme.TextPrimary

@Composable
fun ChangeService(
    service: Service,
    navController: NavHostController,
    serviceViewModel: ServiceViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val context = LocalContext.current
    val name = remember { mutableStateOf(service.name) }
    val price = remember { mutableStateOf(service.price) }
    val photo = remember { mutableStateOf<Bitmap>(service.photo) }
    val imageData = remember { mutableStateOf<Uri?>(null) }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageData.value = uri
        }
    imageData.value?.let {
        if (Build.VERSION.SDK_INT < 28) {
            photo.value = MediaStore.Images
                .Media.getBitmap(context.contentResolver, imageData.value)
        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver, imageData.value!!)
            photo.value = ImageDecoder.decodeBitmap(source)
        }
    }
    when (serviceViewModel.apiStatus) {
        ApiStatus.ERROR -> Toast.makeText(context, "Error: " + serviceViewModel.apiError, Toast.LENGTH_SHORT).show()
        ApiStatus.LOADING -> Loading()
        ApiStatus.DONE ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BlueMain)
                    .padding(15.dp, 0.dp, 15.dp, 60.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "PetMed",
                        style = MaterialTheme.typography.bodyMedium
                            .copy(Color.White, fontSize = TextUnit(8.0f, TextUnitType.Em))
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 0.dp, 10.dp)
                        .height(150.dp)
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(15.dp),
                            clip = false
                        ),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(145.dp)
                            .background(color = Color.White, RoundedCornerShape(15.dp))
                            .padding(15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Image(
                            bitmap = photo.value.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier.align(Alignment.CenterVertically),
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .widthIn(max = (LocalConfiguration.current.screenWidthDp / 3).dp),
                            verticalArrangement = Arrangement.Top,
                        ) {
                            Text(
                                text = service.name,
                                color = TextPrimary,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .widthIn(max = (LocalConfiguration.current.screenWidthDp / 3).dp),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = service.price.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }
                Column(
                ) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .fillMaxWidth()
                            .background(Color.White)
                            .border(2.dp, color = BlueBorder, RoundedCornerShape(15.dp))
                            .height(45.dp)
                            .padding(15.dp, 5.dp),
                    ){
                        BasicTextField(
                            value = name.value,
                            onValueChange = {
                                name.value = it
                            },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = MaterialTheme.typography.bodyMedium,
                            singleLine = true,
                        )
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .fillMaxWidth()
                            .background(Color.White)
                            .border(2.dp, color = BlueBorder, RoundedCornerShape(15.dp))
                            .height(45.dp)
                            .padding(15.dp, 5.dp),
                    ){
                        BasicTextField(
                            value = price.value.toString(),
                            onValueChange = {
                                try {
                                    price.value = it.toDouble()
                                }catch (e: Exception){
                                    price.value = 0.0
                                    serviceViewModel.price.doubleValue = 0.0
                                    Toast.makeText(context, "Input correct price!", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = MaterialTheme.typography.bodyMedium,
                            singleLine = true,
                        )
                    }
                }
                Button(
                    onClick = {
                        launcher.launch("image/*")
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
                    Text(
                        text = "Upload image",
                        style = MaterialTheme.typography.bodyMedium.copy(Color.White)
                    )
                }
                Button(
                    onClick = {
                        if (service.name == ""
                            || service.price == 0.0
                            || price.value == 0.0)
                            Toast.makeText(context, "Input correct value!", Toast.LENGTH_SHORT).show()
                        else{
                            serviceViewModel.updateService(
                                Service(
                                    serviceId = service.serviceId,
                                    name = name.value,
                                    price = price.value,
                                    photo = photo.value
                                )
                            )
                        }
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
                    Text(
                        text = "Save changes",
                        style = MaterialTheme.typography.bodyMedium.copy(Color.White)
                    )
                }
            }

        else -> {}
    }
}