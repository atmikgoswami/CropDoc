package com.example.cropdoc.news.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.cropdoc.R
import com.example.cropdoc.news.data.models.NewsArticle
import com.example.cropdoc.navigation.items1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    navigateToDetail: (NewsArticle) -> Unit,
    newsMainViewModel: NewsMainViewModel = hiltViewModel()
) {
    var selectedItemIndex1 by rememberSaveable {
        mutableStateOf(3)
    }
    val viewState by newsMainViewModel.categoriesState
    Scaffold(
        topBar = {
            TopAppBar(modifier = modifier
                .height(60.dp),
                title = {
                    Box(
                        modifier = modifier.fillMaxSize(),
                    ) {
                        Text(
                            text = "AgriNews",
                            color = colorResource(id = R.color.black),
                            modifier = modifier
                                .padding(top = 8.dp, bottom = 8.dp, start=4.dp)
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
                                .padding(top=12.dp)

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
        Box(modifier = modifier
            .fillMaxSize()
            .padding(it)) {
            when {
                viewState.loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                viewState.error != null -> {
                    Text(text = "ERROR OCCURRED and it is ${viewState.error}")
                }

                else -> {
                    // Display categories
                    NewsCategoryScreen(categories = viewState.list, navigateToDetail)
                }
            }
        }
    }
}

@Composable
fun NewsCategoryScreen(
    categories: List<NewsArticle>,
    navigateToDetail: (NewsArticle) -> Unit
) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(top = 8.dp, bottom = 8.dp)) {
        items(categories) { category ->
            NewsCategoryItem(category = category, navigateToDetail)
        }
    }
}

// How each item looks like
@Composable
fun NewsCategoryItem(category: NewsArticle, navigateToDetail: (NewsArticle) -> Unit) {
    val customGreyColor = Color(android.graphics.Color.parseColor("#ececec"))
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, Color.Gray,RoundedCornerShape(20.dp) )
            .clickable { navigateToDetail(category) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(category.image_url),
            contentDescription = null,
            modifier = Modifier
                .size(125.dp)
                .clip(shape = RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        // Spacer between image and title
        Spacer(modifier = Modifier.width(12.dp))

        // Title on the right side
        category.title?.let {
            Text(
                text = it,
                color = Color.Black,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                modifier = Modifier.weight(1f),
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
                // Take remaining space
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
    }
}