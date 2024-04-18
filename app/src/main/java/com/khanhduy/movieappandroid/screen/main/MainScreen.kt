@file:OptIn(ExperimentalMaterial3Api::class)

package com.khanhduy.movieappandroid.screen.main
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.khanhduy.movieappandroid.screen.main.listMovie.ListMovieScreen
import com.khanhduy.movieappandroid.screen.main.newMovie.NewMovieScreen
import com.khanhduy.movieappandroid.ui.theme.BackgroundColor
import com.khanhduy.movieappandroid.ui.theme.BlueColor
import com.khanhduy.movieappandroid.ui.theme.DarkColor


@Composable
fun MainScreen(navController: NavHostController) {

    val tabs = listOf("New", "TV Series", "Single", "Cartoon")
    var currentIndexTab by rememberSaveable { mutableStateOf(0) }
    var contentSearch by rememberSaveable {
        mutableStateOf("")
    }

    Scaffold(
        containerColor = BackgroundColor
    ) {
        it
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Find Movies, Tv series, and more ...",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(30.dp))
            TextField(
                value = contentSearch,
                onValueChange = {
                    contentSearch = it
                },
                placeholder = {
                    Text(text = "Search...", style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray))
                },
                textStyle = MaterialTheme.typography.bodySmall,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = DarkColor,
                    focusedContainerColor = DarkColor,
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = BlueColor
                ),
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                singleLine =   true
            )
            Spacer(modifier = Modifier.height(20.dp))
            TabRow(
                selectedTabIndex = currentIndexTab,
                containerColor = BackgroundColor,
                divider = {},
                indicator = { tabPositions: List<TabPosition> ->
                    Box {}
                }
            ) {
                tabs.forEachIndexed { index, element ->
                    val selected = currentIndexTab == index
                    Tab(
                        selected = selected,
                        modifier = Modifier
                            .height(40.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (selected) BlueColor else BackgroundColor),
                        onClick = {
                            currentIndexTab = index
                        }
                    ) {
                        Text(
                            text = element,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodySmall,

                            )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            when (currentIndexTab) {
                0 -> NewMovieScreen(navController)
                1 -> ListMovieScreen(1)
                2 -> ListMovieScreen(2)
                3 -> ListMovieScreen(3)
            }
        }

    }
}

