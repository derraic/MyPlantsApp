package com.derra.myplantsapp.plants_list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.derra.myplantsapp.R
import com.derra.myplantsapp.detail_plant.DetailPlantEvent

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlantListNotificationTopScreen(modifier: Modifier = Modifier, viewModel: PlantListViewModel) {
    Column(
        modifier = modifier.padding(start = 20.dp, end = 20.dp)
    ) {

        Spacer(modifier = Modifier.height(60.dp))
        Row(
            modifier = Modifier
                .width(350.dp)
                .height(36.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier
                    .width(36.dp)
                    .height(36.dp)
                    .clickable { viewModel.onEvent(PlantListEvent.UpcomingClick) }
                    .paint(painter = painterResource(id = R.drawable.back_button))) {
            }
            Text(
                modifier = Modifier
                    .width(214.dp)
                    .height(24.dp),
                text = "Notifications",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight(500),
                color = Color(0xFF232926),
                textAlign = TextAlign.Center)

        }
        Spacer(modifier = Modifier.height(26.dp))
        Row() {
            Column(modifier = Modifier.clickable { viewModel.onEvent(PlantListEvent.UpcomingClick) }) {
                Text(
                    modifier = Modifier
                        .width(112.dp)
                        .height(20.dp),
                    text = "All Notifications",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = if (viewModel.selected == "All Notifications") FontWeight(600) else FontWeight(500),color = if (viewModel.selected == "All Notifications") Color(0xFF0A6375)  else Color(0xFFAFB3B7)
                )
                if (viewModel.selected == "All Notifications") {
                    Image(painter = painterResource(id = R.drawable.line_under_text), contentDescription = "blue line")
                }

            }

            Spacer(modifier = Modifier.width(24.dp))
            Column(modifier = Modifier.clickable { viewModel.onEvent(PlantListEvent.ForgotWaterClick) }) {
                Text(modifier = Modifier
                    .width(107.dp)
                    .height(20.dp),text = "Forgot to water", fontSize = 14.sp, fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = if (viewModel.selected == "Forgot") FontWeight(600) else FontWeight(500),color = if (viewModel.selected == "Forgot") Color(0xFF0A6375)  else Color(0xFFAFB3B7)
                )
                if (viewModel.selected == "Forgot") {
                    Image(painter = painterResource(id = R.drawable.line_under_text), contentDescription = "blue line")
                }
            }

            Spacer(modifier = Modifier.width(24.dp))
            Column(modifier = Modifier.clickable { viewModel.onEvent(PlantListEvent.HistoryClick) }) {
                Text(modifier = Modifier
                    .width(49.dp)
                    .height(20.dp),text = "History",  fontSize = 14.sp, fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = if (viewModel.selected == "History") FontWeight(600) else FontWeight(500),color = if (viewModel.selected == "History") Color(0xFF0A6375)  else Color(0xFFAFB3B7)
                )
                if (viewModel.selected == "History") {
                    Image(painter = painterResource(id = R.drawable.line_under_text), contentDescription = "blue line")
                }
            }

        }
        Spacer(modifier = Modifier.height(22.dp))

    }
}