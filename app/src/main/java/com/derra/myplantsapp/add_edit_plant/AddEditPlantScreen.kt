package com.derra.myplantsapp.add_edit_plant

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.derra.myplantsapp.R
import com.derra.myplantsapp.util.UiEvent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddEditPlantScreen(
    onPopBackStack: () -> Unit,
    modifier : Modifier = Modifier,
    viewModel: AddEditPlantViewModel = hiltViewModel(),
    activity: Activity,
    context: Context
) {
    val textFieldColor = colorResource(id = R.color.NeutralusN100)
    val textFieldColors = TextFieldDefaults.textFieldColors(
        backgroundColor = textFieldColor, //Color.Transparent, // Set the background color to transparent
        focusedIndicatorColor = Color.Transparent, // Set the focused indicator color to transparent
        unfocusedIndicatorColor = Color.Transparent // Set the unfocused indicator color to transparent
    )

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        Log.d("URI", "THIs Is the URi: $uri")
        if (uri != null) {
            viewModel.onEvent(AddEditPlantEvent.OnImageChange(context, uri))
        }
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(), onResult = {isGranted ->
        viewModel.onEvent(AddEditPlantEvent.PermissionClick(isGranted))
        if (isGranted) {
            viewModel.onEvent(
                AddEditPlantEvent.OnImageButtonClick(
                    activity,
                    123,
                    galleryLauncher = galleryLauncher
                )
            )
        }

    })
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
                is UiEvent.OpenModal -> {
                    when (event.modal) {
                        "dates" -> viewModel.modalDates = true
                    }
                    when (event.modal) {
                        "time" -> viewModel.modalTime = true
                    }
                    when (event.modal) {
                        "size" -> viewModel.modalPlantSize = true
                    }

                }
                is UiEvent.CloseModal -> {
                    when (event.modal) {
                        "dates" -> viewModel.modalDates = false
                    }
                    when (event.modal) {
                        "time" -> viewModel.modalTime = false
                    }
                    when (event.modal) {
                        "size" -> viewModel.modalPlantSize = false
                    }
                }
                else -> Unit
            }

        }

    }


    Scaffold(scaffoldState = scaffoldState,
        modifier = modifier.fillMaxSize()



    ) {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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


                    if (viewModel.imageString == "none" || viewModel.imageUri == null) {
                        Column(modifier = Modifier
                            .height(180.dp)
                            .width(100.dp)
                            .align(Alignment.Center)){
                            Image(
                                modifier = Modifier
                                    .height(180.dp)
                                    .width(100.dp),
                                painter = painterResource(id = R.drawable.plant_place_holder),
                                contentDescription = "plant image",
                                contentScale = ContentScale.FillBounds
                            )


                        }
                    }
                    else {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            ) {

                            val painter: Painter = rememberAsyncImagePainter(viewModel.imageUri)

                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                                painter = painter,
                                contentDescription = "plant image",
                                contentScale = ContentScale.FillBounds
                            )
                        }
                        
                    }

                    Box(
                        modifier
                            .align(Alignment.TopStart)
                            .padding(start = 20.dp, top = 60.dp)
                            .clickable { viewModel.onEvent(AddEditPlantEvent.OnBackButtonClick) }
                            .paint(painter = painterResource(id = R.drawable.back_button))) {
                    }

                    if (viewModel.imageString == "none"){
                        Column(
                            modifier = Modifier
                                .width(131.dp)
                                .height(56.dp)
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 20.dp)
                        ) {
                            Row(modifier = Modifier
                                .width(131.dp)
                                .height(36.dp)
                                .background(
                                    color = Color(0xFF0A6375),
                                    shape = RoundedCornerShape(size = 8.dp)
                                )
                                .padding(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp)
                                .clickable {

                                    if (viewModel.permission) {
                                        viewModel.onEvent(
                                            AddEditPlantEvent.OnImageButtonClick(
                                                activity,
                                                123,
                                                galleryLauncher = galleryLauncher
                                            )
                                        )
                                    } else {
                                        requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                                    }
                                }, horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Image(modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp) ,painter = painterResource(R.drawable.image_icon_add), contentDescription =  "add_image_icon")
                                Text(modifier = Modifier.width(79.dp).height(20.dp),
                                    text = "Add Image",
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFFFFFFFF),
                                    textAlign = TextAlign.Center)



                            }
                        }
                    }
                    else {
                        Column(
                            modifier = Modifier
                                .width(160.dp)
                                .height(56.dp)
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 20.dp)
                        ) {
                            Row(modifier = Modifier
                                .width(160.dp)
                                .height(36.dp)
                                .background(
                                    color = Color(0xFF0A6375),
                                    shape = RoundedCornerShape(size = 8.dp)
                                )
                                .padding(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp)
                                .clickable {

                                    if (viewModel.permission) {
                                        viewModel.onEvent(
                                            AddEditPlantEvent.OnImageButtonClick(
                                                activity,
                                                123,
                                                galleryLauncher = galleryLauncher
                                            )
                                        )
                                    } else {
                                        requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                                    }
                                }, horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Image(modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp) ,painter = painterResource(R.drawable.image_icon_add), contentDescription =  "add_image_icon")
                                Text(modifier = Modifier.width(107.dp).height(20.dp),
                                    text = "Change Image",
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFFFFFFFF),
                                    textAlign = TextAlign.Center)



                            }
                        }

                    }

                }
                Column(
                    modifier = Modifier
                        .width(390.dp)
                        .height(494.dp)
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(
                                topStart = 24.dp,
                                topEnd = 24.dp,
                                bottomEnd = 0.dp,
                                bottomStart = 0.dp
                            )
                        )
                        .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 10.dp)

                ) {
                    Column(

                    ) {
                        Text(
                            text = "Plant name*", fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF516370))
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
                            TextField(modifier = Modifier
                                .height(48.dp)
                                .fillMaxWidth()
                                ,value = viewModel.title,colors = textFieldColors, textStyle = TextStyle(fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        fontWeight = FontWeight(500),
                                        color = Color(0xFF516370)),onValueChange = {
                                    viewModel.onEvent(AddEditPlantEvent.OnTitleChange(it))
                                })

                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Dates*", fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF516370))
                            Spacer(modifier = Modifier.width(136.dp))
                            Text(text = "Time*", fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF516370))
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)) {

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
                                    .clickable { viewModel.onEvent(AddEditPlantEvent.OnModalDatesClick) },

                                ) {

                                TextField(
                                    modifier = Modifier
                                        .height(48.dp)
                                        .clickable { viewModel.onEvent(AddEditPlantEvent.OnModalDatesClick) },
                                    value = if (viewModel.dates.isNotEmpty()) {
                                        if (viewModel.dates.size == 1) {
                                            // Display single date if there's only one in the list
                                            viewModel.dates[0]
                                        } else {
                                            // Join the first two characters of each day name with commas
                                            viewModel.dates.joinToString(", ") { day ->
                                                day.take(3)
                                            }
                                        }
                                    } else {
                                        ""
                                    },
                                    colors = textFieldColors,
                                    onValueChange = { },
                                    textStyle = TextStyle(fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        fontWeight = FontWeight(500),
                                        color = Color(0xFF516370)),
                                    readOnly = true,

                                    )
                                Box(
                                    modifier
                                        .clickable { viewModel.onEvent(AddEditPlantEvent.OnModalDatesClick) }
                                        .align(Alignment.CenterEnd)
                                        .padding(end = 20.dp)) {
                                    Icon(painter = painterResource(id = R.drawable.drop_down_menu_button), contentDescription = "click to adjust values")
                                }

                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .clickable { viewModel.onEvent(AddEditPlantEvent.OnModalTimeClick) }
                                    //.background(Color.LightGray)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(
                                        width = 1.dp,
                                        color = Color.White,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            ) {
                                TextField(
                                    value = viewModel.time,
                                    onValueChange = {},colors = textFieldColors,
                                    textStyle = TextStyle(fontSize = 14.sp,
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        fontWeight = FontWeight(500),
                                        color = Color(0xFF516370)),
                                    modifier = Modifier
                                        .height(48.dp)
                                        .clickable { viewModel.onEvent(AddEditPlantEvent.OnModalTimeClick) }

                                    ,
                                    readOnly = true
                                )
                                Box(
                                    modifier
                                        .clickable { viewModel.onEvent(AddEditPlantEvent.OnModalTimeClick) }
                                        .align(Alignment.CenterEnd)
                                        .padding(end = 20.dp)) {
                                    Icon(painter = painterResource(id = R.drawable.drop_down_menu_button), contentDescription = "click to adjust values")
                                }

                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "The amount of water*", fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF516370))
                            Spacer(modifier = Modifier.width(37.dp))
                            Text(text = "Plant Size*", fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF516370))
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
                                    value = viewModel.waterAmount + "ml",
                                    colors = textFieldColors,
                                    textStyle = TextStyle(fontSize = 14.sp,
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        fontWeight = FontWeight(500),
                                        color = Color(0xFF516370)),
                                    onValueChange = {
                                        viewModel.onEvent(
                                            AddEditPlantEvent.OnWateredAmountChange(
                                                manipulateText(it)
                                            )
                                        )
                                    },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))

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
                                    .clickable { viewModel.onEvent(AddEditPlantEvent.OnModalPlantSizeClick) },

                                ) {

                                TextField(
                                    modifier = Modifier
                                        .clickable { viewModel.onEvent(AddEditPlantEvent.OnModalPlantSizeClick) }
                                        .height(48.dp),colors = textFieldColors,
                                    value = viewModel.plantSize,
                                    textStyle = TextStyle(fontSize = 14.sp,
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        fontWeight = FontWeight(500),
                                        color = Color(0xFF516370)),
                                    onValueChange = { },
                                    readOnly = true,
                                )

                                Box(
                                    modifier
                                        .clickable { viewModel.onEvent(AddEditPlantEvent.OnModalPlantSizeClick) }
                                        .align(Alignment.CenterEnd)
                                        .padding(end = 20.dp)) {
                                    Icon(painter = painterResource(id = R.drawable.drop_down_menu_button), contentDescription = "click to adjust values")
                                }

                            }


                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Description", fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight(500),
                                color = Color(0xFF516370))
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
                            TextField(value = viewModel.description,colors = textFieldColors, textStyle = TextStyle(fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF516370)), onValueChange = {
                                viewModel.onEvent(AddEditPlantEvent.OnDescriptionChange(it))
                            }, modifier = Modifier
                                .fillMaxWidth()
                                .height(65.dp))
                        }
                        Spacer(modifier = Modifier.height(3.dp))
                        Row(modifier = Modifier
                            .width(350.dp)
                            .height(48.dp)
                            .background(
                                color = Color(0xFF0A6375),
                                shape = RoundedCornerShape(size = 10.dp)
                            )
                            .padding(start = 20.dp, top = 12.dp, end = 20.dp, bottom = 12.dp),
                            verticalAlignment = Alignment.Top,) {
                            Text(
                                modifier = Modifier
                                    .width(310.dp)
                                    .height(24.dp)
                                ,
                                text = viewModel.buttonString,
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight(500),
                                color = Color(0xFFFFFFFF),
                                textAlign = TextAlign.Center)


                        }

                    }

                }


            }

        ModalDates(modifier = Modifier, viewModel = viewModel)
        ModalSize(modifier = Modifier, viewModel = viewModel)
            ModalTime(modifier = Modifier, viewModel = viewModel)
        }

    }

}


private fun manipulateText(text: String): String {
    val number = text.filter { it.isDigit() }
    return number.ifEmpty {
        ""
    }
}