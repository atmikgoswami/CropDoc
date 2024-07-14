package com.example.cropdoc.crop_suggestion.presentation

import CropData
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cropdoc.R
import getCropDetails
import java.util.Locale

@Composable
fun get_crop_suggestion(
    cropData: CropData,
    cropViewModel: CropSuggestionViewModel
) {
    LaunchedEffect(key1 = null) {
        cropViewModel.predictCrop(cropData)
    }
    val cropSuggestionState  = cropViewModel.suggestionState.value

    when {
        cropSuggestionState.loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(top=16.dp))
        }
        cropSuggestionState.error != null -> {
            Text(
                text = "An error occurred: ${cropSuggestionState.error}"
            )
        }
        cropSuggestionState.suggestion != null -> {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
                .clip(RoundedCornerShape(10))
                .border(2.dp, Color.Gray, RoundedCornerShape(10))
                .background(MaterialTheme.colorScheme.primaryContainer)
                ,
                contentAlignment = Alignment.Center
            )
            {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    val suggestion = cropSuggestionState.suggestion
                    Text(text = "CROP RECOMMENDED : ${suggestion.uppercase(Locale.getDefault())}",
                        color = Color.Black,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center
                        ),
                    )
                    val imageResource = when (suggestion.lowercase(Locale.getDefault())) {
                        "blackgram" -> R.drawable.blackgram
                        "rice" -> R.drawable.rice
                        "chickpea" -> R.drawable.chickpea
                        "cotton" -> R.drawable.cotton
                        "jute" -> R.drawable.jute
                        "kidneybeans" -> R.drawable.kidneybean
                        "lentil" -> R.drawable.lentil
                        "maize" -> R.drawable.maize
                        "mothbeans" -> R.drawable.mothbeans
                        "mungbean" -> R.drawable.mungbean
                        "muskmelon" -> R.drawable.muskmelon
                        "pigeonpeas" -> R.drawable.pigeonpeas
                        "watermelon" -> R.drawable.watermelon

                        else -> R.drawable.img_2

                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Image(
                        painter = painterResource(id = imageResource),
                        contentDescription = "Recommended Crop Image",
                        modifier = Modifier.size(250.dp).border(2.dp,
                            Color.Gray, RoundedCornerShape(10)
                        ).clip(
                            RoundedCornerShape(10)
                        ),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = getCropDetails(suggestion),
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