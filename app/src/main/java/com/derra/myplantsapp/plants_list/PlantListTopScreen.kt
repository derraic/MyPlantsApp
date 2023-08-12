package com.derra.myplantsapp.plants_list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.derra.myplantsapp.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlantListTopScreen(
    modifier: Modifier = Modifier, viewModel: PlantListViewModel
) {
    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Row (modifier = Modifier
            .height(70.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column (modifier = Modifier
                .height(70.dp)
                .width(279.dp)) {
                Text(text = "Let’s Care",
                        fontSize = 24.sp,
                    fontFamily = FontFamily(
                        Font(R.font.poppins_regular)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF232926))
                Text(text = "My Plants!", fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF232926)
                )
            }
            Box(modifier = Modifier.width(40.dp)
                .height(40.dp).clickable { viewModel.onEvent(PlantListEvent.OnAllNotificationsClick) }) {
                Image(modifier = Modifier.align(Alignment.Center), painter = painterResource(id = R.drawable.circle_notification_button), contentDescription = "Notification Button")
                Icon(modifier = Modifier.align(Alignment.Center), painter = painterResource(id = R.drawable.notification_icon), contentDescription = "Notification")
                if (viewModel.plantNotificationsUnOpened > 0) {
                    Box(
                        modifier = Modifier
                            .width(6.dp)
                            .height(6.dp)
                            .background(
                                color = Color(0xFFFC1B1B),
                                shape = RoundedCornerShape(size = 6.dp)
                            )
                            .padding(top = 2.dp)
                            .align(Alignment.TopEnd)
                            .offset(x = (-5).dp, y = 2.dp)



                    ) {

                    }
                }


            }

        }
        Spacer(modifier = Modifier.height(20.dp))
        Row() {
            Column(modifier = Modifier.clickable { viewModel.onEvent(PlantListEvent.UpcomingClick) }) {
                Text(
                    modifier = Modifier
                        .width(74.dp)
                        .height(20.dp),
                    text = "Upcoming",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = if (viewModel.selected == "Upcoming") FontWeight(600) else FontWeight(500),color = if (viewModel.selected == "Upcoming") Color(0xFF0A6375)  else Color(0xFFAFB3B7)
                )
                if (viewModel.selected == "Upcoming") {
                    Image(painter = painterResource(id = R.drawable.line_under_text), contentDescription = "blue line")
                }

            }

            Spacer(modifier = Modifier.width(24.dp))
            Column(modifier = Modifier.clickable { viewModel.onEvent(PlantListEvent.ForgotWaterClick) }) {
                Text(modifier = Modifier
                    .width(107.dp)
                    .height(20.dp),text = "Forgot to water", fontSize = 14.sp, fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = if (viewModel.selected == "Forgot") FontWeight(600) else FontWeight(500),color = if (viewModel.selected == "Forgot") Color(0xFF0A6375)  else Color(0xFFAFB3B7))
                if (viewModel.selected == "Forgot") {
                    Image(painter = painterResource(id = R.drawable.line_under_text), contentDescription = "blue line")
                }
            }

            Spacer(modifier = Modifier.width(24.dp))
            Column(modifier = Modifier.clickable { viewModel.onEvent(PlantListEvent.HistoryClick) }) {
                Text(modifier = Modifier
                    .width(49.dp)
                    .height(20.dp),text = "History",  fontSize = 14.sp, fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = if (viewModel.selected == "History") FontWeight(600) else FontWeight(500),color = if (viewModel.selected == "History") Color(0xFF0A6375)  else Color(0xFFAFB3B7))
                if (viewModel.selected == "History") {
                    Image(painter = painterResource(id = R.drawable.line_under_text), contentDescription = "blue line")
                }
            }

        }



    }





}