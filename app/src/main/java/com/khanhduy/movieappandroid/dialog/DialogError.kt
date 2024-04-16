package com.khanhduy.movieappandroid.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.khanhduy.movieappandroid.R
import com.khanhduy.movieappandroid.ui.theme.BackgroundColor
import com.khanhduy.movieappandroid.ui.theme.BlueColor
import com.khanhduy.movieappandroid.ui.theme.ButtonColor

@Composable
fun DiaLogError(
    message: String,
    onDismissRequest: () -> Unit
){
    Dialog(onDismissRequest = {onDismissRequest()}) {
        Column (
            modifier = Modifier
                .background(BackgroundColor)
                .padding(horizontal = 20.dp, vertical = 30.dp)
                .clip(RoundedCornerShape(10.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_logo),
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Error", style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = message, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(30.dp))
            ElevatedButton(
                onClick = {
                    onDismissRequest()
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BlueColor
                )

            ) {
                Text(text = "Continue", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}