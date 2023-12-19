package com.example.myapplication.composeui.List_of_Services

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.businessLogic.viewmodel.AppViewModelProvider
import com.example.myapplication.businessLogic.viewmodel.ServiceViewModel
import com.example.myapplication.composeui.Navbar.NavItem
import com.example.myapplication.composeui.UIComponents.MyTextField
import com.example.myapplication.model.Service
import com.example.myapplication.ui.theme.BlueMain
import com.example.myapplication.ui.theme.GreenBtn
import com.example.myapplication.ui.theme.TextPrimary
import kotlinx.coroutines.Dispatchers

@Composable
fun AddService (navController: NavController, service: Service, serviceViewModel: ServiceViewModel = viewModel(factory = AppViewModelProvider.Factory)){
    val create = service.serviceId == null
    LaunchedEffect(Dispatchers.Default){
        if(!create){
            serviceViewModel.service.value.serviceId = service.serviceId
            serviceViewModel.service.value.photo = service.photo
        }
    }
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
                if(create){
                    Image(
                        painter = painterResource(id = serviceViewModel.photo.intValue),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight()
                            .heightIn(min = 100.dp)
                            .widthIn(max = (LocalConfiguration.current.screenWidthDp / 3).dp),
                        contentScale = ContentScale.FillHeight,
                    )
                }else{
                    service.photo?.let { painterResource(id = it) }?.let {
                        Image(
                            painter = it,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxHeight()
                                .heightIn(min = 100.dp)
                                .widthIn(max = (LocalConfiguration.current.screenWidthDp / 3).dp),
                            contentScale = ContentScale.FillHeight,
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .widthIn(max = (LocalConfiguration.current.screenWidthDp / 3).dp),
                    verticalArrangement = Arrangement.Top,
                ){
                    if(create){
                        Text(
                            text = serviceViewModel.name.value,
                            color = TextPrimary,
                            style = MaterialTheme.typography.bodyMedium)
                    }else{
                        Text(
                            text = service.name,
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
                    if(create){
                        Text(
                            text = serviceViewModel.price.doubleValue.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }else{
                        Text(
                            text = service.price.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
        Column (
        ){
            Row (modifier = Modifier.padding(vertical = 5.dp)){
                if(create){
                    MyTextField(label = "Service name"){
                        serviceViewModel.name.value = it
                    }
                }else{
                    MyTextField(label = service.name){
                        serviceViewModel.service.value.name = it
                    }
                }
            }
            Row (modifier = Modifier.padding(vertical = 5.dp)){
                if(create){
                    MyTextField(label = "Price"){
                        serviceViewModel.price.doubleValue = it.toDouble()
                    }
                }else{
                    MyTextField(label = service.price.toString()){
                        serviceViewModel.service.value.price = it.toDouble()
                    }
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
                if (create)
                    serviceViewModel.insertService()
                else
                    serviceViewModel.service.let { serviceViewModel.updateService() }
                navController.navigate(NavItem.ListOfServices.route)
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
            if(create) {
                Text(text = "Add service", style = MaterialTheme.typography.bodyMedium.copy(Color.White))
            }else{
                Text(text = "Update service", style = MaterialTheme.typography.bodyMedium.copy(Color.White))
            }
        }
    }
}
