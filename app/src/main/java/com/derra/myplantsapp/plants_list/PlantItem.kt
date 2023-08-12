package com.derra.myplantsapp.plants_list

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.derra.myplantsapp.R
import com.derra.myplantsapp.add_edit_plant.AddEditPlantEvent
import com.derra.myplantsapp.add_edit_plant.AddEditPlantViewModel
import com.derra.myplantsapp.data.Plant
import com.derra.myplantsapp.util.DateTimeUtils
import java.io.File


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlantItem(
    plant: Plant,
    viewModel: PlantListViewModel,
    onEvent: (PlantListEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier

            .width(167.dp)
            .height(256.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = {

                    onEvent(PlantListEvent.DeletePlantHold(plant.name))
                }
                , onTap = {onEvent(PlantListEvent.OnPlantClick(plant)) }
                )
            }
            .background(
                color = Color.White,
                shape = RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp,
                    bottomEnd = 10.dp,
                    bottomStart = 10.dp
                )
            )
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.NeutralusN100),
                shape = RoundedCornerShape(10.dp)
            ),


    ) {
        Column(modifier = Modifier.fillMaxSize()

        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(196.dp)
                    .background(
                        color = Color(0xFFF5F9F3),
                        shape = RoundedCornerShape(
                            topStart = 10.dp,
                            topEnd = 10.dp,
                            bottomEnd = 0.dp,
                            bottomStart = 0.dp
                        )
                    ),
                contentAlignment = Alignment.Center
            )
            {
                if (plant.image == "none") {
                    Image(
                        painter = painterResource(id = R.drawable.plant_place_holder),
                        contentDescription = "Image",
                        modifier = Modifier
                            .width(60.dp)
                            .height(108.dp)
                    )
                } else {
                    if (plant.image != null) {
                        viewModel.onEvent(PlantListEvent.RetrieveImage(plant.image!!))
                    }


                    val painter: Painter = rememberAsyncImagePainter(viewModel.imageUri)
                    Image(
                        painter = painter,
                        contentDescription = "plant picture",
                        contentScale = ContentScale.FillBounds
                    )

                }
                Column(modifier = Modifier
                    .padding(start = 12.dp, top = 12.dp)
                    .align(Alignment.TopStart)) {
                    Box(
                        Modifier
                            .width(38.dp)
                            .height(20.dp)
                            .background(
                                color = Color(0x40222222),
                                shape = RoundedCornerShape(size = 4.dp)
                            )
                            .padding(start = 6.dp, top = 2.dp, end = 6.dp, bottom = 2.dp)) {

                        Text(
                            modifier = Modifier
                                .width(26.dp)
                                .height(16.dp),
                            text = plant.waterAmount + "ml",
                            fontSize = 10.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF))
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier


                            .width(if(DateTimeUtils.formatDateTime(plant.date) == "Tomorrow") 58.dp else 44.dp)
                            .height(20.dp)
                            .background(
                                color = Color(0x40222222),
                                shape = RoundedCornerShape(size = 4.dp)
                            )
                            .padding(start = 5.dp, top = 2.dp, end = 3.dp, bottom = 2.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .width(if(DateTimeUtils.formatDateTime(plant.date) == "Tomorrow") 50.dp else 37.dp)
                                .height(16.dp),
                            text = DateTimeUtils.formatDateTime(plant.date),
                            fontSize = 10.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF))

                    }
                }
            }


            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier
                .padding(horizontal = 12.dp)
                .width(143.dp)
                .height(36.dp)
                ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                ) {
                Column(modifier = Modifier
                    .width(95.dp)
                    .height(36.dp)) {

                    Text(
                        modifier = Modifier
                            .width(95.dp)
                            .height(20.dp),
                        text = plant.name,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF232926)
                    )

                    Text(
                        modifier = Modifier
                            .width(95.dp)
                            .height(16.dp),
                        text = plant.description ?: "",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        color = Color(0xFF516370),
                        overflow = TextOverflow.Ellipsis

                    )
                }
                val isWatered = plant.watered
                if (!plant.watered) {
                    Box(modifier = Modifier
                        .width(24.dp)
                        .height(24.dp)
                        .background(
                            color = Color(0xFF0A6375),
                            shape = RoundedCornerShape(size = 4.dp)
                        )
                        .padding(start = 4.dp, top = 4.dp, end = 4.dp, bottom = 4.dp),
                        contentAlignment = Alignment.Center) {

                        Image(
                            modifier = Modifier
                                .height(16.dp)
                                .width(16.dp)
                                .clickable { onEvent(PlantListEvent.WateredButtonClick(plant)) },
                            painter = painterResource(id = R.drawable.water_icon),
                            contentDescription = "Water Dripping"
                        )
                    }
                }
                else {
                    Box(modifier = Modifier
                        .width(24.dp)
                        .height(24.dp)
                        .background(
                            color = Color(0xFF8CB6B2),
                            shape = RoundedCornerShape(size = 4.dp)
                        )
                        .padding(start = 4.dp, top = 4.dp, end = 4.dp, bottom = 4.dp), contentAlignment = Alignment.Center)
                    {
                        Image(
                            modifier = Modifier
                                .height(16.dp)
                                .width(16.dp),
                            painter = painterResource(id = R.drawable.check_is_watered),
                            contentDescription = "Watered: Check"
                        )
                    }
                }


            }


        }
    }
}


