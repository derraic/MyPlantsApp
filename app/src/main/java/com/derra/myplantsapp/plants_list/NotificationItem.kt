package com.derra.myplantsapp.plants_list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.derra.myplantsapp.data.Notification
import com.derra.myplantsapp.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotificationItem(notification: Notification, viewModel: PlantListViewModel) {
    Row(
        modifier = Modifier
            .border(width = 1.dp, color = Color(0xFFF9F9F9))
            .width(390.dp)
            .height(128.dp)
            .background(color = Color(0xFFFFFFFF))
            .padding(top = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.Top,
    ) {

        Column(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)) {
                Row(
                    modifier = Modifier
                        .width(297.dp)
                        .height(40.dp), horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .paint(painter = painterResource(id = com.derra.myplantsapp.R.drawable.notification_plant_icon))) {
                    }
                    Column(
                        modifier = Modifier
                            .width(249.dp)
                            .height(40.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            modifier = Modifier
                                .width(160.dp)
                                .height(20.dp),
                            text = "Daily plant notification",
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF232926))

                        Text(
                            text = notification.time,
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            color = Color(0xFF516370))
                    }
                    
                }
                Spacer(modifier = Modifier.width(41.dp))
                if (!notification.clicked) {
                    Box(Modifier
                        .width(12.dp)
                        .height(12.dp)
                        .background(color = Color(0xFFFC1B1B), shape = RoundedCornerShape(size = 12.dp))) {

                    }
                }

                
            }

            Text(
                modifier = Modifier.width(339.dp).height(20.dp),
                text = notification.title,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight(500),
                color = Color(0xFF516370))


            Text(
                modifier = Modifier.clickable {
                    if (notification.plantId == -1) {
                        viewModel.onEvent(PlantListEvent.NotificationClick(notification))
                    }
                    else {
                        viewModel.onEvent(PlantListEvent.PlantNotificationClick(notification.plantId))
                    }

                },
                text = notification.clickableText,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight(600),
                color = Color(0xFF0A6375))


        }


    }


}