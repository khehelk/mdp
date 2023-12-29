package com.example.myapplication.composeui.List_of_Services

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
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
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.api.ApiStatus
import com.example.myapplication.businessLogic.viewmodel.AppViewModelProvider
import com.example.myapplication.businessLogic.viewmodel.ServiceViewModel
import com.example.myapplication.composeui.Navbar.NavItem
import com.example.myapplication.composeui.NetworkUI.Loading
import com.example.myapplication.composeui.UIComponents.MyTextField
import com.example.myapplication.ui.theme.BlueMain
import com.example.myapplication.ui.theme.GreenBtn
import com.example.myapplication.ui.theme.TextPrimary

@Composable
fun AddService (
    navController: NavController,
    serviceViewModel: ServiceViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val context = LocalContext.current
    val serviceImage = remember { mutableStateOf<Bitmap>(BitmapFactory.decodeResource(context.resources, R.drawable.image_service)) }
    val imageData = remember { mutableStateOf<Uri?>(null) }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageData.value = uri
        }
    imageData.value?.let {
        if (Build.VERSION.SDK_INT < 28) {
            serviceImage.value = MediaStore.Images
                .Media.getBitmap(context.contentResolver, imageData.value)
        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver, imageData.value!!)
            serviceImage.value = ImageDecoder.decodeBitmap(source)
        }
    }

    if (serviceViewModel.apiStatus == ApiStatus.ERROR) {
        Toast.makeText(context, "Error: " + serviceViewModel.apiError, Toast.LENGTH_SHORT).show()
        return
    }
    when (serviceViewModel.apiStatus) {
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
                        verticalAlignment = CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Image(
                            bitmap = serviceImage.value.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier.align(CenterVertically),
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .widthIn(max = (LocalConfiguration.current.screenWidthDp / 3).dp),
                            verticalArrangement = Arrangement.Top,
                        ) {
                            Text(
                                text = serviceViewModel.name.value,
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
                                text = serviceViewModel.price.doubleValue.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }
                Column(
                ) {
                    Row(modifier = Modifier.padding(vertical = 5.dp)) {
                        MyTextField(label = "Service name") {
                            serviceViewModel.name.value = it
                        }
                    }
                    Row(modifier = Modifier.padding(vertical = 5.dp)) {
                        MyTextField(label = "Price") {
                            try {
                                serviceViewModel.price.doubleValue = it.toDouble()
                            }catch (e: Exception){
                                serviceViewModel.price.doubleValue = 0.0
                                Toast.makeText(context, "Input correct price!", Toast.LENGTH_SHORT).show()
                            }
                        }
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
                        if (serviceViewModel.name.value == ""
                            || serviceViewModel.price.doubleValue == 0.0)
                            Toast.makeText(context, "Input correct value!", Toast.LENGTH_SHORT).show()
                        else {
                            serviceViewModel.insertService(serviceImage.value)
                            navController.navigate(NavItem.AddService.route)
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
                        text = "Add service",
                        style = MaterialTheme.typography.bodyMedium.copy(Color.White)
                    )
                }
            }

        else -> {}
    }
}