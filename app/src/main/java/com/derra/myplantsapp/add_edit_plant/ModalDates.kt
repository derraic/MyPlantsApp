package com.derra.myplantsapp.add_edit_plant

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.derra.myplantsapp.R
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.draw.paint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ModalDates(
    modifier: Modifier,
    viewModel: AddEditPlantViewModel,


) {
    val checkboxColors = CheckboxDefaults.colors(
        checkedColor = colorResource(id = R.color.AccentA500), // Customize the color when checked
        // = BorderStroke(2.dp, Color.Black) // Customize the border color and stroke width
    )

    val items = remember {
        listOf(
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday",
            "Sunday"
        )
    }
    if (viewModel.modalDates) {
        Log.d("MODALDATES", "IT GETS RECOMPOSED")
        AlertDialog(
            onDismissRequest = { viewModel.onEvent(AddEditPlantEvent.CancelButtonClick) },
            title = { Text(text = "Dates",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF232926)) },
            text = {
                Column(modifier = modifier) {
                    val currDates: List<String> by viewModel.currDatesStateFlow.collectAsState()
                    items.forEach { item ->
                        Row(
                            modifier = Modifier
                                //.offset(x = 0.dp, y = 48.dp)
                                .width(350.dp)
                                .height(56.dp)
                                .padding(start = 20.dp, top = 16.dp, end = 44.dp, bottom = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start),
                                verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                modifier = Modifier
                                    //.offset(x = 0.dp, y = 2.dp)
                                    .width(20.dp)
                                    .height(20.dp),
                                colors = checkboxColors,
                                checked = currDates.contains(item),
                                onCheckedChange = {
                                    Log.d("MODALDATES", "THIS is it: $it and this is item: $item")
                                    viewModel.onEvent(AddEditPlantEvent.OnDayChange(it, item))},

                            )
                            Text(
                                modifier = Modifier
                                    //.offset(x = 32.dp, y = 0.dp)
                                    .width(254.dp)
                                    .height(24.dp)
                                    .padding(start = 12.dp)
                                ,
                                text = item,
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight(500),
                                color = if (!currDates.contains(item)) Color(0xFF516370) else Color(0xFF232926)
                                )
                        }
                    }
                    Row(
                        modifier = Modifier
                            //.offset(x = 0.dp, y = 496.dp)
                            .width(350.dp)
                            .height(88.dp)
                            .padding( bottom = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(modifier = Modifier
                            //.offset(x = 20.dp, y = 16.dp)
                            .border(
                                width = 1.dp,
                                color = Color(0xFFF9F9F9),
                                shape = RoundedCornerShape(size = 10.dp)
                            )
                            .width(151.dp)
                            .height(48.dp)
                            .clickable { viewModel.onEvent(AddEditPlantEvent.CancelButtonClick) }
                            .padding(start = 20.dp, top = 12.dp, end = 20.dp, bottom = 12.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .width(111.dp)
                                    .height(24.dp),
                                text = "Cancel",
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight(500),
                                color = Color(0xFF516370),
                                textAlign = TextAlign.Center)
                        }
                        Box(
                            modifier = Modifier
                                //.background(color = Color(0xFF0A6375), shape = RoundedCornerShape(size = 10.dp))
                                //.padding(start = 20.dp, top = 12.dp, end = 20.dp, bottom = 12.dp)
                                .width(151.dp)
                                .height(48.dp)
                                .background(color = Color(0xFF0A6375), shape = RoundedCornerShape(size = 10.dp))
                                .padding(start = 20.dp, top = 12.dp, end = 20.dp, bottom = 12.dp)
                                .clickable { viewModel.onEvent(AddEditPlantEvent.SaveButtonDatesClick) }
                                //.paint(painter = painterResource(id = R.drawable.button_got_it_good))
                        ){
                            Text(
                                modifier = Modifier
                                    //.offset(x = 20.dp, y = 12.dp)
                                    .width(111.dp)
                                    .height(24.dp),
                                text = "Got it",
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFFFFFFFF),
                                    textAlign = TextAlign.Center)
                        }

                    }

                }
            },
            confirmButton = {
                //Button(onClick = {viewModel.onEvent(AddEditPlantEvent.SaveButtonDatesClick)}) {
                    //Text(text = "Got it")
                //}
            },
            dismissButton = {
                //Button(onClick = { viewModel.onEvent(AddEditPlantEvent.CancelButtonClick) }) {
                //    Text(text = "Cancel")
                //}
            },
            modifier = Modifier
                //.offset(x = 20.dp, y = 130.dp)
                .shadow(
                    elevation = 36.dp,
                    spotColor = Color(0x29000000),
                    ambientColor = Color(0x29000000)
                )
                .width(350.dp)
                .height(584.dp)
                .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 16.dp))
                .padding(top = 8.dp)
        )
    }





}