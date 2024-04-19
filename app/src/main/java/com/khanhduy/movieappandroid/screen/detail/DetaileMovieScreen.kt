package com.khanhduy.movieappandroid.screen.detail

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.khanhduy.movieappandroid.GraphRoot
import com.khanhduy.movieappandroid.R
import com.khanhduy.movieappandroid.data.api.ApiConstant
import com.khanhduy.movieappandroid.models.DetailMovieModel
import com.khanhduy.movieappandroid.ui.theme.BackgroundColor
import com.khanhduy.movieappandroid.ui.theme.BlackColor
import com.khanhduy.movieappandroid.ui.theme.BlueColor
import com.khanhduy.movieappandroid.ui.theme.LightGrey
import com.khanhduy.movieappandroid.ui.theme.WhiteColor

@Composable
fun DetailMovieScreen(slug: String, navController: NavController) {
    val viewModel: DetailMovieViewModel = hiltViewModel<DetailMovieViewModel>()
    val detailState = viewModel.detailState.collectAsState()

    var isLoading by rememberSaveable {
        mutableStateOf(true)
    }

    var detailMovieModel: DetailMovieModel? by rememberSaveable {
        mutableStateOf(null)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(DetailMovieEvent.Init(slug))
    }

    LaunchedEffect(key1 = detailState.value){
        Log.e("bbb", "LaunchedEffect When ${detailState.value.toString()}", )
        when (detailState.value) {
            is DetailMovieState.Loading -> {
                isLoading = true
            }

            is DetailMovieState.Success -> {
                detailMovieModel = (detailState.value as DetailMovieState.Success).detailMovieModel
                isLoading = false
            }

            is DetailMovieState.Error -> {

            }
            is DetailMovieState.Init -> {
                isLoading = true
            }
        }
    }


    Scaffold(
        containerColor = BackgroundColor
    ) {
        Log.e("bbb", "Build UI", )
        it
        if (isLoading && detailMovieModel == null) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(
                    color = Color.White
                )
            }
        } else {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                ) {
                    AsyncImage(
                        model = detailMovieModel!!.movie.thumb_url,
                        contentDescription = null,
                        modifier = Modifier
                            .height(250.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        BackgroundColor.copy(alpha = 0.9f),
                                        BackgroundColor
                                    )
                                )
                            )
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .align(Alignment.BottomCenter)
                    ) {
                        Box {
                            AsyncImage(
                                model = detailMovieModel!!.movie.poster_url,
                                contentDescription = null,
                                modifier = Modifier
                                    .height(200.dp)
                                    .width(150.dp)
                                    .clip(RoundedCornerShape(10.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = detailMovieModel!!.movie.name,
                                style = MaterialTheme.typography.titleSmall
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Thời gian: ${detailMovieModel!!.movie.time}",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Ngôn ngữ: ${detailMovieModel!!.movie.lang}",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            if (detailMovieModel!!.movie.status == "ongoing") {
                                Text(
                                    text = "Trạng thái: ${detailMovieModel!!.movie.episode_current} / ${detailMovieModel!!.movie.episode_total}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            } else {
                                Text(
                                    text = "Trạng thái: ${detailMovieModel!!.movie.episode_current} ",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            ElevatedButton(
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = BlueColor
                                ),
                                shape = RoundedCornerShape(5.dp),
                                onClick = {
                                    navController.currentBackStackEntry?.savedStateHandle?.set<DetailMovieModel>(
                                        key = "detailMovieModel",
                                        value = detailMovieModel
                                    )
                                    navController.currentBackStackEntry?.savedStateHandle?.set<Int>(
                                        key = "episode",
                                        value = 0
                                    )
                                    navController.navigate(GraphRoot.PlayVideo.route)
                                }
                            ) {
                                Text(
                                    text = "Xem Phim",
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            }

                        }
                    }
                }

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Nội Dung",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = detailMovieModel!!.movie.content,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Thông Tin",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Thể Loại: ${detailMovieModel!!.movie.getCategories()}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Năm sản xuất: ${detailMovieModel!!.movie.year}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Đạo diễn: ${detailMovieModel!!.movie.getDirector()}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Diễn viên: ${detailMovieModel!!.movie.getActor()}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Quốc gia: ${detailMovieModel!!.movie.getCountry()}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Lượt Xem: ${detailMovieModel!!.movie.view}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Chất lượng: ${detailMovieModel!!.movie.quality}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    detailMovieModel!!.episodes.forEachIndexed { index, episodes ->
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Server ${index + 1}",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            episodes.server_data.forEachIndexed { index, it ->

                                ElevatedButton(
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.LightGray
                                    ),
                                    shape = RoundedCornerShape(5.dp),
                                    onClick = {

                                    }
                                ) {
                                    Text(
                                        text = "${index+1}",
                                        style = MaterialTheme.typography.bodySmall.copy(color = BlackColor),
                                    )
                                }

                            }
                        }

                    }

                }
            }
        }

    }
}