package com.derra.myplantsapp.add_edit_plant

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.derra.myplantsapp.R
import com.derra.myplantsapp.util.UiEvent



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddEditPlantScreen(
    onPopBackStack: () -> Unit,
    modifier : Modifier = Modifier,
    viewModel: AddEditPlantViewModel = hiltViewModel()
) {
    val textFieldColor = colorResource(id = R.color.NeutralusN100)
    val textFieldColors = TextFieldDefaults.textFieldColors(
        backgroundColor = textFieldColor, //Color.Transparent, // Set the background color to transparent
        focusedIndicatorColor = Color.Transparent, // Set the focused indicator color to transparent
        unfocusedIndicatorColor = Color.Transparent // Set the unfocused indicator color to transparent
    )

    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                else -> Unit
            }

        }

    }
    Scaffold(scaffoldState = scaffoldState,
        modifier = modifier.fillMaxSize()



    ) {

        Column(modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.OtherG100))
            .paint(
                painter = painterResource(id = R.drawable.background_main_page),
                alignment = Alignment.TopCenter
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)


            ) {

                Column(modifier = Modifier.align(Alignment.Center)){
                    Image(

                        painter = painterResource(id = R.drawable.plant_place_holder),
                        contentDescription = "plant image"
                    )
                }

                Column(
                    modifier = Modifier
                        .width(131.dp)
                        .height(36.dp)
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 20.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .width(160.dp)
                            .height(36.dp),
                        onClick = { /*TODO*/ }) {
                        Row() {
                            Text(
                                text = "Change Image",
                                style = TextStyle(fontSize = 14.sp),
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(494.dp)
                    .background(color = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp)
                    ) {
                    Text(text = "Plant name*", fontSize = 14.sp, lineHeight = 20.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                width = 1.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        OutlinedTextField(modifier = Modifier
                            .height(48.dp)
                            .fillMaxWidth()
                            ,value = viewModel.title,colors = textFieldColors, textStyle = TextStyle(fontSize = 14.sp),onValueChange = {
                            viewModel.onEvent(AddEditPlantEvent.OnTitleChange(it))
                        })
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Dates*", fontSize = 14.sp, lineHeight = 20.sp)
                        Spacer(modifier = Modifier.width(133.dp))
                        Text(text = "Time*", fontSize = 14.sp, lineHeight = 20.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)) {
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
                        val expanded = remember { mutableStateOf(false) }
                        Box(
                            modifier = Modifier
                                .height(48.dp)
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    width = 1.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable { expanded.value = true },

                            ) {

                            OutlinedTextField(
                                modifier = Modifier
                                    .height(48.dp),
                                value = if (viewModel.dates.isNotEmpty()) viewModel.dates.joinToString(
                                    ", "
                                ) else "",colors = textFieldColors,
                                onValueChange = { },
                                textStyle = MaterialTheme.typography.body1,
                                readOnly = true,

                                )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        val sizes = remember {
                            listOf(
                                "Small",
                                "Medium",
                                "Large",
                                "Extra Large"
                            )
                        }
                        val expanded1 = remember { mutableStateOf(false) }
                        Box(
                            modifier = Modifier
                                .height(48.dp)
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    width = 1.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable { expanded1.value = true },

                            ) {

                            TextField(
                                modifier = Modifier
                                    .height(48.dp),colors = textFieldColors,
                                value = viewModel.plantSize,
                                onValueChange = { },
                                readOnly = true,
                            )

                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "The amount of water*", fontSize = 14.sp, lineHeight = 20.sp)
                        Spacer(modifier = Modifier.width(65.dp))
                        Text(text = "Plant Size*", fontSize = 14.sp, lineHeight = 20.sp)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    )
                    {


                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    width = 1.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(8.dp)
                                )
                        ) {
                            TextField(
                                modifier = Modifier.height(48.dp),
                                value = viewModel.waterAmount.toString() + "ml",
                                colors = textFieldColors,
                                onValueChange = {
                                    viewModel.onEvent(
                                        AddEditPlantEvent.OnWateredAmountChange(
                                            it.dropLast(2).toInt()
                                        )
                                    )
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        }
                            Spacer(modifier = Modifier.width(12.dp))

                            var expanded3 by remember { mutableStateOf(false) }
                            var selectedHour by remember { mutableStateOf(0) }
                            var selectedMinute by remember { mutableStateOf(0) }

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    //.background(Color.LightGray)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(
                                        width = 1.dp,
                                        color = Color.White,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            ) {
                                TextField(
                                    value = "$selectedHour:$selectedMinute",
                                    onValueChange = {},colors = textFieldColors,
                                    modifier = Modifier
                                        .height(48.dp)
                                        .clickable { expanded3 = true }
                                        ,
                                    readOnly = true
                                )

                            }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Description", fontSize = 14.sp, lineHeight = 20.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(65.dp)
                            .clip(RoundedCornerShape(8.dp))
                            //.background(color = Color.LightGray)
                            .border(
                                width = 1.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        TextField(value = viewModel.description,colors = textFieldColors, maxLines = 3, onValueChange = {
                            viewModel.onEvent(AddEditPlantEvent.OnDescriptionChange(it))
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .height(65.dp))
                    }
                    Spacer(modifier = Modifier.height(6.dp))

                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .fillMaxWidth()
                            .paint(painter = painterResource(id = R.drawable.button_big_for_real))
                            .clickable { viewModel.onEvent(AddEditPlantEvent.OnSavePlantClick) }
                            .align(Alignment.CenterHorizontally)

                    ) {
                        Text(
                            text = viewModel.buttonString,
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        )
                    }
                }

            }


        }
    }

}