package com.derra.myplantsapp.plants_list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.derra.myplantsapp.R
import com.derra.myplantsapp.util.DateTimeUtils

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlantListNotificationScreen(modifier: Modifier = Modifier, viewModel: PlantListViewModel) {
    val notifications = viewModel.notifications.collectAsState(initial = emptyList())
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.OtherG100))
            .paint(
                painter = painterResource(id = R.drawable.background_main_page),
                alignment = Alignment.TopCenter
            )) {
        PlantListNotificationTopScreen(viewModel = viewModel)
        Column(
            modifier = Modifier
                .width(390.dp)
                .height(686.dp)
                .background(
                    color = Color(color = 0xFFFFFFFF),
                    shape = RoundedCornerShape(
                        topStart = 24.dp,
                        topEnd = 24.dp,
                        bottomEnd = 0.dp,
                        bottomStart = 0.dp
                    )
                )
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
        ) {

            val daysUsed = mutableListOf<String>()
            LazyColumn() {
                items(notifications.value) {notification ->
                    val dayText = DateTimeUtils.formatRelativeDate(notification.date)
                    if (!daysUsed.contains(dayText)) {
                        daysUsed.add(dayText)
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                modifier = Modifier
                                    .width(91.dp)
                                    .height(20.dp),
                                text = "Today",
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight(500),
                                color = Color(0xFF516370))
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                    NotificationItem(notification = notification, viewModel = viewModel)


                }

            }


        }

        
    }

}