package com.derra.myplantsapp.plants_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.derra.myplantsapp.R

@Composable
fun PlantListTopScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Row (modifier = Modifier
            .height(64.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column (modifier = Modifier
                .height(64.dp)
                .width(279.dp)) {
                Text(text = "Let's Care", style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold))
                Text(text = "My Plants!", style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold))
            }
            Box(contentAlignment = Alignment.Center) {
                Image(painter = painterResource(id = R.drawable.circle_notification_button), contentDescription = "Notification Button")
                Icon(painter = painterResource(id = R.drawable.notification_icon), contentDescription = "Notification")

            }

        }
        Spacer(modifier = Modifier.height(20.dp))
        Row() {
            Text(text = "Upcoming", fontSize = 14.sp, lineHeight = 20.sp)
            Spacer(modifier = Modifier.width(24.dp))
            Text(text = "Forgot to water", fontSize = 14.sp, lineHeight = 20.sp)
            Spacer(modifier = Modifier.width(24.dp))
            Text(text = "History",  fontSize = 14.sp, lineHeight = 20.sp)
        }



    }





}