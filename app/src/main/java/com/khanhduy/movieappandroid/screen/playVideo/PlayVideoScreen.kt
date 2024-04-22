package com.khanhduy.movieappandroid.screen.playVideo

import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.khanhduy.movieappandroid.models.DetailMovieModel
import com.khanhduy.movieappandroid.ui.theme.BackgroundColor
import com.khanhduy.movieappandroid.ui.theme.BlackColor
import com.khanhduy.movieappandroid.ui.theme.BlueColor
import com.khanhduy.movieappandroid.ui.theme.LocalAppDimens

@Composable
fun PlayVideoScreen(
    detailMovieModel: DetailMovieModel?,
    episode: Int?,
    navController: NavController
) {

    var currentIndexServer by rememberSaveable {
        mutableStateOf(0)
    }

    var currentIndexEpisode by rememberSaveable {
        mutableStateOf(episode)
    }

    val context = LocalContext.current

    var playWhenReady by remember { mutableStateOf(true) }

    val mediaItem =
        MediaItem.Builder().setUri(detailMovieModel!!.episodes.first().server_data[0].link_m3u8)
            .setMimeType(
                MimeTypes.APPLICATION_M3U8
            ).build()

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(mediaItem)
            repeatMode = ExoPlayer.REPEAT_MODE_ALL
            playWhenReady = playWhenReady
            prepare()
            play()
        }
    }


    Scaffold(
        containerColor = BackgroundColor
    ) {
        it
        Column(

        ) {
            DisposableEffect(
                Box(
                    modifier = Modifier.aspectRatio(16 / 9f)
                ) {
                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = {
                            PlayerView(context).apply {
                                player = exoPlayer
                                useController = true
                                FrameLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT
                                )
                            }
                        }
                    )
                }

            ) {
                onDispose {
                    exoPlayer.release()
                }
            }
            Column(
                Modifier
                    .padding(LocalAppDimens.current.spacerMedium)
                    .verticalScroll(rememberScrollState())
            )
            {
                infomation(detailMovieModel = detailMovieModel)

                episodes(
                    detailMovieModel = detailMovieModel,
                    currentIndexServer = currentIndexServer,
                    currentIndexEpisode = currentIndexEpisode!!
                )
            }
        }


    }

}

@Composable
private fun infomation(detailMovieModel: DetailMovieModel) {
    Column {
        Text(
            text = detailMovieModel.movie.name,
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(LocalAppDimens.current.spacerMedium))
        Text(
            text = "Nội Dung",
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(LocalAppDimens.current.spacerSmall))
        Text(
            text = detailMovieModel!!.movie.content,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(LocalAppDimens.current.spacerMedium))
        Text(
            text = "Thông Tin",
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(LocalAppDimens.current.spacerSmall))
        Text(
            text = "Thể Loại: ${detailMovieModel!!.movie.getCategories()}",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(LocalAppDimens.current.spacerSmall))
        Text(
            text = "Năm sản xuất: ${detailMovieModel!!.movie.year}",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(LocalAppDimens.current.spacerSmall))
        Text(
            text = "Đạo diễn: ${detailMovieModel!!.movie.getDirector()}",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(LocalAppDimens.current.spacerSmall))
        Text(
            text = "Diễn viên: ${detailMovieModel!!.movie.getActor()}",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(LocalAppDimens.current.spacerSmall))
        Text(
            text = "Quốc gia: ${detailMovieModel!!.movie.getCountry()}",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(LocalAppDimens.current.spacerSmall))
        Text(
            text = "Lượt Xem: ${detailMovieModel!!.movie.view}",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(LocalAppDimens.current.spacerSmall))
        Text(
            text = "Chất lượng: ${detailMovieModel!!.movie.quality}",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(LocalAppDimens.current.spacerSmall))
    }
}

@Composable
private fun episodes(
    detailMovieModel: DetailMovieModel,
    currentIndexServer: Int,
    currentIndexEpisode: Int
) {
    Column {
        detailMovieModel!!.episodes.forEachIndexed { indexServer, episodes ->
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Server ${indexServer + 1}",
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
                            containerColor = if (currentIndexServer == indexServer && currentIndexEpisode == index) BlueColor else Color.LightGray
                        ),
                        shape = RoundedCornerShape(5.dp),
                        onClick = {

                        }
                    ) {
                        Text(
                            text = "${index + 1}",
                            style = MaterialTheme.typography.bodySmall.copy(color = BlackColor),
                        )
                    }

                }
            }

        }
    }
}