package com.derra.myplantsapp.plants_list

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlantListEmptyScreen(modifier: Modifier = Modifier, viewModel: PlantListViewModel, scaffoldState: ScaffoldState) {
    Scaffold(scaffoldState = scaffoldState,modifier = Modifier.fillMaxSize()) {
        Column(modifier = modifier
            .fillMaxSize()
            //.background(Color(R.color.AccentN0))
            .paint(
                painter = painterResource(
                    id = R.drawable.background_filled_page
                ),
                alignment = Alignment.TopCenter
            )
            //.background(Color(R.color.AccentN0))
            .padding(start = 20.dp, end = 20.dp)
        ) {
            PlantListTopScreen(viewModel = viewModel)
            Spacer(modifier = Modifier.height(322.dp))
            Column(modifier = Modifier.padding(44.dp)) {
                Row(modifier = Modifier
                    .width(262.dp)
                    .height(24.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Text(text = "Sorry.", fontSize = 16.sp ,fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF232926))

                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier
                    .width(262.dp)
                    .height(40.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Text(text = "There are no plants in the list, please add your first plant.", fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF516370),
                            textAlign = TextAlign.Center, maxLines = 2)

                }
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .height(48.dp)
                        .width(262.dp)
                        .paint(painter = painterResource(id = R.drawable.button_big_for_real))
                        .clickable { viewModel.onEvent(PlantListEvent.OnAddPlantClick) }
                        .align(Alignment.CenterHorizontally)

                ) {
                    //Text(text = "Add Your First Plant", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold))
                }


            }



        }
    }





}
