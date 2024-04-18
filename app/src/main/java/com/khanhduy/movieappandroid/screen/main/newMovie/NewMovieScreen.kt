package com.khanhduy.movieappandroid.screen.main.newMovie

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.khanhduy.movieappandroid.GraphRoot
import com.khanhduy.movieappandroid.dialog.DiaLogError
import com.khanhduy.movieappandroid.models.ItemNewMovie
import com.khanhduy.movieappandroid.models.NewMovieModel
import com.khanhduy.movieappandroid.ui.theme.BackgroundColor
import com.khanhduy.movieappandroid.ui.theme.HintColor
import com.khanhduy.movieappandroid.ui.theme.WhiteColor
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun NewMovieScreen(navController: NavController) {
    val newMovieViewModel: NewMovieViewModel = hiltViewModel<NewMovieViewModel>()
    val newMovieState by newMovieViewModel.uiState.collectAsState()


    var isLoading by rememberSaveable() {
        mutableStateOf(true)
    }
    var newMovieModel: NewMovieModel? = null

    var currentPage by rememberSaveable {
        mutableStateOf(1)
    }

    var startPage by rememberSaveable {
        mutableStateOf(1)
    }

    var totalPageDisplay by rememberSaveable {
        mutableStateOf(currentPage + 6)
    }

    var messageError by rememberSaveable {
        mutableStateOf("")
    }

    var isShowDialogErro by rememberSaveable {
        mutableStateOf(false)
    }

    val scrollState = rememberLazyGridState()


    LaunchedEffect(key1 = currentPage) {
        newMovieViewModel.onEvent(NewMovieEvent.GetNewMovie(currentPage))
    }

    when (newMovieState) {
        is NewMovieState.Innit -> {
            Log.e("bbb", "loading")
            isLoading = true
        }
        is NewMovieState.Loading -> {
            Log.e("bbb", "loading")
            isLoading = true
        }
        is NewMovieState.Success -> {
            Log.e("bbb", "loading success")
            newMovieModel = (newMovieState as NewMovieState.Success).newMovieModel
            isLoading = false
            rememberCoroutineScope().launch {
                scrollState.scrollToItem(0, 0)
            }

        }
        is NewMovieState.Error -> {
            isShowDialogErro = true
            messageError = (newMovieState as NewMovieState.Error).message
        }
    }

    Scaffold(
        containerColor = BackgroundColor
    ) {
        it

        if (newMovieState is NewMovieState.Error) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Refresh,
                    contentDescription = null,
                    tint = WhiteColor,
                    modifier = Modifier.height(50.dp).width(50.dp)
                        .clickable {
                                   newMovieViewModel.onEvent(NewMovieEvent.GetNewMovie(currentPage))
                        },
                )
            }
            if(isShowDialogErro){
                DiaLogError(message = messageError) {
                    isShowDialogErro = false
                }
            }

        } else {
            if (isLoading || newMovieModel == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        color = Color.White
                    )
                }
            } else {
                LazyVerticalGrid(
                    state = scrollState,
                    columns = GridCells.Adaptive(128.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(30.dp),
                ) {
                    items(newMovieModel.items) { movie ->
                        NewMovieItem(movie = movie, navController = navController)
                    }
                    item(
                        span = { GridItemSpan(2) }
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.KeyboardArrowLeft,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.clickable {
                                        if (startPage != 1) {
                                            startPage -= 4
                                            totalPageDisplay -= 4
                                        }
                                    }
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.weight(1f),
                                ) {
                                    for (i in startPage..totalPageDisplay) {
                                        if (currentPage == i) {
                                            Text(
                                                text = i.toString(),
                                                style = MaterialTheme.typography.bodySmall,
                                                modifier = Modifier
                                                    .background(color = HintColor)
                                                    .padding(10.dp),
                                                maxLines = 1
                                            )
                                        } else {
                                            Text(
                                                text = i.toString(),
                                                modifier = Modifier
                                                    .padding(10.dp)
                                                    .clickable {
                                                        currentPage = i
                                                        if (currentPage == totalPageDisplay) {
                                                            startPage = totalPageDisplay
                                                            totalPageDisplay = startPage + 4
                                                        }

                                                    },
                                                style = MaterialTheme.typography.bodySmall,
                                                maxLines = 1
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Icon(
                                    Icons.Default.KeyboardArrowRight,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.clickable {
                                        if (totalPageDisplay + 4 >= newMovieModel.pagination.totalPages) {
                                            startPage += 4
                                            totalPageDisplay += newMovieModel.pagination.totalPages - totalPageDisplay
                                        } else {
                                            startPage += 4
                                            totalPageDisplay += 4
                                        }
                                    }
                                )
                            }
                        }

                    }
                }

            }
        }
    }
}

@Composable
fun NewMovieItem(movie: ItemNewMovie, navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            navController.navigate("${GraphRoot.Detail.route}/${movie.slug}")
        }
    ) {
        AsyncImage(
            model = movie.poster_url,
            contentDescription = null,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = movie.name, style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
    }

}