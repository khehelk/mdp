package com.example.myapplication.composeui.Profile

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.GlobalUser
import com.example.myapplication.api.ApiStatus
import com.example.myapplication.businessLogic.viewmodel.AppViewModelProvider
import com.example.myapplication.businessLogic.viewmodel.UserViewModel
import com.example.myapplication.composeui.Navbar.NavItem
import com.example.myapplication.composeui.NetworkUI.Loading
import com.example.myapplication.model.User
import com.example.myapplication.ui.theme.BlueBorder
import com.example.myapplication.ui.theme.BlueMain
import com.example.myapplication.ui.theme.GreenBtn

@Composable
fun ProfileChange (navController: NavHostController, userViewModel: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)){
    val user = GlobalUser.getInstance().getUser()
    val name = remember { mutableStateOf(user?.name) }
    val surname = remember { mutableStateOf(user?.surname) }
    val email = remember { mutableStateOf(user?.email) }
    val password = remember { mutableStateOf(user?.password) }
    val context = LocalContext.current
    val imageData = remember { mutableStateOf<Uri?>(null) }
    val profileImage = remember { mutableStateOf<Bitmap>(user?.photo!!) }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {uri: Uri? ->
            imageData.value = uri
        }
    imageData.value?.let {
        if (Build.VERSION.SDK_INT < 28) {
            profileImage.value = MediaStore.Images
                .Media.getBitmap(context.contentResolver, imageData.value)
        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver, imageData.value!!)
            profileImage.value = ImageDecoder.decodeBitmap(source)
        }
    }
    var isEmailValid by remember { mutableStateOf(true) }
    var isPasswordValid by remember { mutableStateOf(true) }

    when(userViewModel.apiStatus){
        ApiStatus.LOADING -> Loading()
        ApiStatus.DONE -> Column (
            modifier = Modifier
                .fillMaxSize()
                .background(BlueMain)
                .padding(15.dp)
                .padding(bottom = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "PetMed",
                    style = MaterialTheme.typography.bodyMedium
                        .copy(Color.White, fontSize = TextUnit(8.0f, TextUnitType.Em))
                )
            }
            Box(modifier = Modifier
                .clip(CircleShape)
                .size(200.dp)
                .background(Color.White)
                .padding(PaddingValues(0.dp))
            ){
                    Image(
                        bitmap = profileImage.value.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center),
                        )
            }
            Box(modifier = Modifier.padding(15.dp)){
                Text(
                    text = "${name.value} ${surname.value}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    textAlign = TextAlign.Center,
                )
            }
            Box(modifier = Modifier.padding(15.dp)){
                Text(
                    text = "${email.value}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    textAlign = TextAlign.Center,
                )
            }
            Column (
            ){
                Spacer(modifier = Modifier.size(5.dp))
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
                        value = name.value!!,
                        onValueChange = {
                            name.value = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyMedium,
                        singleLine = true,
                    )
                }
                Spacer(modifier = Modifier.size(5.dp))
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
                        value = surname.value!!,
                        onValueChange = {
                            surname.value = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyMedium,
                        singleLine = true,
                    )
                }
                Spacer(modifier = Modifier.size(5.dp))
                if (!isEmailValid) {
                    Text(
                        text = "Invalid email format",
                        style = MaterialTheme.typography.bodyMedium
                            .copy(Color.Red, fontSize = TextUnit(4.0f, TextUnitType.Em))
                    )
                }
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
                        value = email.value!!,
                        onValueChange = {
                            email.value = it
                            isEmailValid = isValidEmail(email.value!!)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyMedium,
                        singleLine = true,
                    )
                }
                Spacer(modifier = Modifier.size(5.dp))
                if (!isPasswordValid) {
                    Text(
                        text = "Password is required",
                        style = MaterialTheme.typography.bodyMedium
                            .copy(Color.Red, fontSize = TextUnit(4.0f, TextUnitType.Em))
                    )
                }
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
                        value = password.value.toString(),
                        onValueChange = {
                            password.value = it
                            isPasswordValid = it.isNotEmpty()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyMedium,
                        singleLine = true,
                    )
                }
            }
            Spacer(modifier = Modifier.size(5.dp))
            Button(
                onClick = {
                    launcher.launch("image/*")
                },
                modifier = Modifier
                    .height(50.dp)
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
            Spacer(modifier = Modifier.size(5.dp))
            Button(
                onClick = {
                    if (!isEmailValid
                        || !isPasswordValid
                        || name.value == ""
                        || surname.value == "")
                        Toast.makeText(context, "Input correct value!", Toast.LENGTH_SHORT).show()
                    else {
                        val updatedUser = User(
                            user?.userId!!,
                            name = name.value!!,
                            surname = surname.value!!,
                            email = email.value!!,
                            password = password.value!!,
                            role = user.role,
                            photo = profileImage.value,
                            basketId = user.basketId)
                        userViewModel.updateUser(updatedUser)
                        GlobalUser.getInstance().setUser(updatedUser)
                        navController.navigate(NavItem.Profile.route)
                    }
                },
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .clip(CircleShape),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GreenBtn,
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(0.dp),
            ) {
                Text(text = "Confirm changes", style = MaterialTheme.typography.bodyMedium.copy(Color.White))
            }
        }

        else -> {}
    }
}
fun isValidEmail(str: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(str).matches()
}