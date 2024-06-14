package com.example.cropdoc.disease_prediction.presentation

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.PhotoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cropdoc.R
import com.example.cropdoc.navigation.items1
import com.example.cropdoc.disease_prediction.data.models.getDiseaseSolutions
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DiseasePredictionScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    context: Context,
    diseasePredictionViewModel: DiseaseViewModel = hiltViewModel()
) {
    var getPredictionButtonClicked by remember {
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
        mutableStateOf(1)
    }
    Scaffold(
        topBar = {
            TopAppBar(modifier = modifier
                .height(60.dp),
                title = {
                    Box(
                        modifier = modifier.fillMaxSize(),
                    ) {
                        Text(
                            text = "Disease Identification",
                            color = colorResource(id = R.color.black),
                            modifier = modifier
                                .padding(top = 8.dp, bottom = 8.dp, start = 4.dp)
                                .align(Alignment.CenterStart),
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            ),
                        )
                    }

                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = Color.Black,
                            contentDescription = "back",
                            modifier = modifier
                                .size(38.dp)
                                .padding(top = 12.dp)

                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.inverseOnSurface) {
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        )
        {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item{
                    Box(modifier = modifier
                        .fillMaxSize()
                        .size(300.dp)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(10))
                        .border(width = 1.dp, color = Color.Black, RoundedCornerShape(10))
                    )
                    {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxSize(),
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
                    )
                    {
                        Icon(Icons.Rounded.PhotoLibrary,
                            contentDescription = "Select Image",
                            modifier = modifier.size(25.dp)

                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        getPredictionButtonClicked  = true } )
                    {
                        Text(text = "Predict Disease")
                    }
                    if(getPredictionButtonClicked){
                        if(imageUri!=Uri.EMPTY)
                            upload(imageUri = imageUri, context = context, diseasePredictionViewModel = diseasePredictionViewModel)
                        else
                            Toast.makeText(context, "Please Select Image First", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}

@Composable
fun upload(imageUri: Uri, context: Context, diseasePredictionViewModel: DiseaseViewModel = hiltViewModel()){

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


        val diseasePredictionState  = diseasePredictionViewModel.predictionState.value
        when {
            diseasePredictionState.loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(top=16.dp))
            }
            diseasePredictionState.error != null -> {
                Text(
                    text = "An error occurred: ${diseasePredictionState.error}",
                )
            }
            diseasePredictionState.prediction != null -> {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(top=16.dp)
                    .clip(RoundedCornerShape(10))
                    .border(1.dp, Color.Gray, RoundedCornerShape(10))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val prediction = diseasePredictionState.prediction

                    Text(text = "Predicted : $prediction",
                        color = Color.Black,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = getDiseaseSolutions(prediction).trim(),
                        color = Color.Black,
                        style = TextStyle(
                            fontSize = 18.sp,
                        ),
                    )
                }
            }
        }
    }
}

