package com.example.cropdoc.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.cropdoc.AppBarView
import com.example.cropdoc.R
import com.example.cropdoc.data.NewsArticle
import java.util.Locale

@Composable
fun CategoryDetailScreen(navController: NavController, category: NewsArticle) {
    Scaffold(
        topBar = {
            AppBarView(
                fontSize = 42.sp,
                stringResourceId = R.string.news_screen_app_bar,
            ) { navController.navigateUp() }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                category.title?.let {
                    Text(
                        text = it.uppercase(Locale.ROOT),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        ),
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Image(
                    painter = rememberAsyncImagePainter(category.urlToImage),
                    contentDescription = null,
                    modifier = Modifier
                        .wrapContentSize()
                        .aspectRatio(1f)
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
                )

                category.description?.let {
                    Text(
                        text = it, textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(8.dp),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                }
            }
        }
    }
}