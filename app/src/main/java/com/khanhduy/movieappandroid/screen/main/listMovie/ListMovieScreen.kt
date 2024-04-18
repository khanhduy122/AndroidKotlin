package com.khanhduy.movieappandroid.screen.main.listMovie

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
import coil.compose.AsyncImage
import com.khanhduy.movieappandroid.data.api.ApiConstant
import com.khanhduy.movieappandroid.dialog.DiaLogError
import com.khanhduy.movieappandroid.models.ListMovieItem
import com.khanhduy.movieappandroid.models.ListMovieModel
import com.khanhduy.movieappandroid.ui.theme.BackgroundColor
import com.khanhduy.movieappandroid.ui.theme.BlueColor
import com.khanhduy.movieappandroid.ui.theme.HintColor
import com.khanhduy.movieappandroid.ui.theme.WhiteColor
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ListMovieScreen(indexTab: Int) {
    val listMovieViewModel: ListMovieViewModel = hiltViewModel<ListMovieViewModel>()
    val listMovieState by listMovieViewModel.uiState.collectAsState()


    var isLoading by rememberSaveable() {
        mutableStateOf(true)
    }
    var listMovieModel: ListMovieModel? = null

    var currentPage by rememberSaveable {
        mutableStateOf(1)
    }

    var startPage by rememberSaveable {
        mutableStateOf(1)
    }

    var totalPageDisplay by rememberSaveable {
        mutableStateOf(currentPage + 6)
    }

    var isShowDialogError by rememberSaveable {
        mutableStateOf(false)
    }

    var messageError by rememberSaveable {
        mutableStateOf("")
    }

    val scrollState = rememberLazyGridState()



    LaunchedEffect(key1 = currentPage) {
        when (indexTab) {
            1 -> listMovieViewModel.onEvent(ListMovieEvent.GetSeriesMovie(currentPage))
            2 -> listMovieViewModel.onEvent(ListMovieEvent.GetSingleMovie(currentPage))
            3 -> listMovieViewModel.onEvent(ListMovieEvent.GetCartoonMovie(currentPage))
        }
    }

    when (listMovieState) {
        is ListMovieState.Innit -> {
            Log.e("bbb", "loading")
            isLoading = true
        }

        is ListMovieState.Loading -> {
            Log.e("bbb", "loading")
            isLoading = true
        }

        is ListMovieState.Success -> {
            Log.e("bbb", "loading success")
            listMovieModel = (listMovieState as ListMovieState.Success).listMovieModel
            isLoading = false
            rememberCoroutineScope().launch {
                scrollState.scrollToItem(0, 0)
            }

        }

        is ListMovieState.Error -> {
            messageError = (listMovieState as ListMovieState.Error).message
            isShowDialogError = true
        }
    }

    Scaffold(
        containerColor = BackgroundColor
    ) {
        it
        if (listMovieState is ListMovieState.Error) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Refresh,
                    contentDescription = null,
                    tint = WhiteColor,
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .clickable {
                            when (indexTab) {
                                1 -> listMovieViewModel.onEvent(ListMovieEvent.GetSeriesMovie(currentPage))
                                2 -> listMovieViewModel.onEvent(ListMovieEvent.GetSingleMovie(currentPage))
                                3 -> listMovieViewModel.onEvent(ListMovieEvent.GetCartoonMovie(currentPage))
                            }
                        },
                )
            }
            if (isShowDialogError) {
                DiaLogError(message = messageError) {
                    isShowDialogError = false
                }
            }

        } else {

            if (isLoading || listMovieModel == null) {
                Box(
                    Modifier.fillMaxSize(),
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
                    items(listMovieModel.data.items) { movie ->
                        ListMovieItem(movie)
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
                                        if (totalPageDisplay == listMovieModel.data.params.pagination.totalPages) {
                                            return@clickable
                                        }
                                        if (totalPageDisplay + 4 >= listMovieModel.data.params.pagination.totalPages) {
                                            startPage += 4
                                            totalPageDisplay += listMovieModel.data.params.pagination.totalPages - totalPageDisplay
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
fun ListMovieItem(movie: ListMovieItem) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            AsyncImage(
                model = "${ApiConstant.domainImge}${movie.poster_url}",
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop,
            )
            Box(
                Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 10.dp, top = 10.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(BlueColor)
            ) {
                Text(
                    text = movie.quality, style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = movie.name,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    }

}