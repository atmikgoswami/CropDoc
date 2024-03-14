package com.example.cropdoc

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cropdoc.data.items1

@Composable
fun AppBarView(
    fontSize: TextUnit,
    stringResourceId: Int,
    onBackNavClicked: () -> Unit= {}
) {
    val navigationIcon: (@Composable () -> Unit)? =
        if (stringResourceId != R.string.home_screen_app_bar) {
            {
                IconButton(onClick = { onBackNavClicked() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = Color.White,
                        contentDescription = "back",
                        modifier = Modifier
                        .size(35.dp)
                    )
                }
            }
        } else {
            null
        }


        TopAppBar(modifier = Modifier
            .height(90.dp),
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
//                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = stringResourceId),
                        color = colorResource(id = R.color.white),
                        modifier = Modifier
//                            .padding(start = 2.dp)
                            .heightIn(max = 48.dp)
//                            .align(Alignment.Center),
                                ,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = fontSize
                        ),
                    )
                }
            },
            elevation = 10.dp,
            backgroundColor = colorResource(id = R.color.customgreen),
            navigationIcon = navigationIcon
        )
}

