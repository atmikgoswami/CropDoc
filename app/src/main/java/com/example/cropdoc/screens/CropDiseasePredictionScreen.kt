package com.example.cropdoc.screens

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PhotoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cropdoc.AppBarView
import com.example.cropdoc.R
import com.example.cropdoc.ViewModels.DiseasePredictionViewModel
import com.example.cropdoc.data.items1
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DiseasePredictionScreen(
    navController: NavController,
    context: Context,
    diseasePredictionViewModel: DiseasePredictionViewModel
) {
    var get_prediction_button_clicked by remember {
        mutableStateOf(false)}
    var imageUri: Uri by remember { mutableStateOf(Uri.EMPTY) }
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        if (it != null) {
            Log.d("PhotoPicker", "Selected URI: $it")
            imageUri = it
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }
    var selectedItemIndex1 by rememberSaveable {
        mutableStateOf(0)
    }
    Scaffold(
        topBar = {
            AppBarView(
                fontSize = 30.sp,
                stringResourceId = R.string.crop_disease_prediction_app_bar,
            ) { navController.navigateUp() }
        },
        bottomBar = {
            NavigationBar {
                items1.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex1 == index,
                        onClick = {
                            selectedItemIndex1 = index
                            navController.navigate(item.route)
                        },
                        label = {
                            Text(text = item.title)
                        },
                        alwaysShowLabel = true,
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex1) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        }
                    )
                }
            }
        }

    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .padding(top = 30.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(width = 1.dp, color = Color.Black),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUri)
                        .crossfade(enable = true)
                        .build(),
                    contentDescription = "Leaf Image",
                    contentScale = ContentScale.Crop,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            IconButton(
                onClick = {
                    photoPicker.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
                modifier = Modifier.size(100.dp),
            ) {
                Icon(Icons.Rounded.PhotoLibrary, contentDescription = "Select Image")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                get_prediction_button_clicked  = true } )

            {
                Text(text = "Predict Disease")
            }
        }
        if(get_prediction_button_clicked  ){
            if(imageUri!=Uri.EMPTY)
                upload(imageUri = imageUri, context = context, diseasePredictionViewModel = diseasePredictionViewModel)
            else
                Toast.makeText(context, "Please Select Image First", Toast.LENGTH_LONG).show()
        }
    }
}

@Composable
fun upload(imageUri: Uri, context: Context, diseasePredictionViewModel: DiseasePredictionViewModel){

    val filesDir = context.filesDir
    val file = File(filesDir, "crop.jpeg")
    val inputStream = context.contentResolver.openInputStream(imageUri)
    val outputStream = FileOutputStream(file)
    inputStream!!.copyTo(outputStream)

    val requestBody = file.asRequestBody("crop/*".toMediaTypeOrNull())
    val part = MultipartBody.Part.createFormData("crop",file.name, requestBody)

    Log.d("hello", file.name)

    LaunchedEffect(key1 = null) {
        diseasePredictionViewModel.predictDisease(part)
        Log.d("hi","${diseasePredictionViewModel.predictionState.value}")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        val diseasePredictionState  = diseasePredictionViewModel.predictionState.value

        when {
            diseasePredictionState.loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            diseasePredictionState.error != null -> {
                Text(
                    text = "An error occurred: ${diseasePredictionState.error}",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            diseasePredictionState.prediction != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 350.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val prediction = diseasePredictionState.prediction

                    Text(text = "Predicted : ${prediction.uppercase(Locale.getDefault())}",
                        color = Color.Black,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }
    }
}

