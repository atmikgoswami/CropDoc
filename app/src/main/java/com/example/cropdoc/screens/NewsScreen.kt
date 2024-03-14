package com.example.cropdoc.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.cropdoc.AppBarView
import com.example.cropdoc.R
import com.example.cropdoc.ViewModels.NewsMainViewModel
import com.example.cropdoc.data.NewsArticle
import com.example.cropdoc.data.items1

@Composable
fun NewsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewState: NewsMainViewModel.NewsState,
    navigateToDetail: (NewsArticle) -> Unit
) {
    var selectedItemIndex1 by rememberSaveable {
        mutableStateOf(0)
    }
    Scaffold(
        topBar = {
            AppBarView(
                fontSize = 42.sp,
                stringResourceId = R.string.news_screen_app_bar,
            ) {navController.navigateUp()}
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
        Box(modifier = modifier.fillMaxSize().padding(it)) {
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
    LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 8.dp, bottom = 8.dp)) {
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
            .padding(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(customGreyColor)
            .clickable { navigateToDetail(category) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        // Image on the left side
        Image(
            painter = rememberAsyncImagePainter(category.urlToImage),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(shape = RoundedCornerShape(8.dp)) // Apply corner radius
        )

        // Spacer between image and title
        Spacer(modifier = Modifier.width(8.dp))

        // Title on the right side
        category.title?.let {
            Text(
                text = it,
                color = Color.Black,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                modifier = Modifier.weight(1f) // Take remaining space
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
    }
}