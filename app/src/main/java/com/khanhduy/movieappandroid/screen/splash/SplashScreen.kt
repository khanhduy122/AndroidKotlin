package com.khanhduy.movieappandroid.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.khanhduy.movieappandroid.GraphRoot
import com.khanhduy.movieappandroid.R
import com.khanhduy.movieappandroid.dialog.DiaLogError
import com.khanhduy.movieappandroid.ui.theme.BackgroundColor
import com.khanhduy.movieappandroid.ui.theme.BlueColor
import com.khanhduy.movieappandroid.ui.theme.ButtonColor
import com.khanhduy.movieappandroid.ui.theme.LocalAppDimens


@Composable
fun SplashScreen (navController: NavController){
    
    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            BackgroundColor
                        )
                    )
                )
        )
        Column (
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "FLIXAURA",
                style = MaterialTheme.typography.titleMedium
            )

            Box (modifier = Modifier.height(20.dp))

            Text(
                text = "No matter what your mood or preference, flixaura has the perfect movie or show to match.",
                style = MaterialTheme.typography.bodySmall
            )

            Box (modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(LocalAppDimens.current.buttonHeight),
                colors = ButtonDefaults.buttonColors(BlueColor),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    navController.navigate(GraphRoot.Main.route)
                },
            ) {
                Text(text = "Get Started")
            }

            Box (modifier = Modifier.height(40.dp))
        }

    }
}