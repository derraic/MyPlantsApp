package com.derra.myplantsapp.detail_plant

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.derra.myplantsapp.R
import com.derra.myplantsapp.add_edit_plant.AddEditPlantEvent
import com.derra.myplantsapp.util.UiEvent

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailPlantScreen(
    modifier: Modifier = Modifier,
    onPopBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: DetailPlantViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = true) {
        viewModel.fetchData()
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.Navigate -> onNavigate(event)

                else -> Unit
            }
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.OtherG100))
        .paint(
            painter = painterResource(id = R.drawable.background_main_page),
            alignment = Alignment.TopCenter
        )) {
        
        Box(modifier = Modifier
            .width(390.dp)
            .height(487.dp))
        {

            if (viewModel.imageString == "none" || viewModel.imageUri == null) {
                Column(modifier = Modifier
                    .height(200.dp)
                    .width(100.dp)
                    .align(Alignment.Center).padding(bottom = 20.dp)){
                    Image(
                        modifier = Modifier.height(180.dp)
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
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, top = 60.dp, end = 20.dp)) {
                Row(modifier = Modifier
                    .width(350.dp)
                    .height(36.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                ) {
                    Box(
                        modifier
                            .clickable { viewModel.onEvent(DetailPlantEvent.OnBackButtonClick) }
                            .paint(painter = painterResource(id = R.drawable.back_button))) {
                    }
                    Box(
                        modifier
                            .clickable { viewModel.onEvent(DetailPlantEvent.OnEditButtonClick) }
                            .paint(painter = painterResource(id = R.drawable.edit_navigation_button))) {
                    }

                }
                Spacer(modifier = Modifier.height(262.dp))
                Row(modifier = Modifier
                    .padding(horizontal = 42.dp)
                    .width(266.dp)
                    .height(60.dp)
                    .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 8.dp))
                    .padding(start = 12.dp, top = 12.dp, end = 12.dp, bottom = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,) {
                    Column(
                        modifier = Modifier
                            .width(50.dp)
                            .height(36.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                        horizontalAlignment = Alignment.Start,
                    ) {

                        Text(
                            modifier = Modifier
                                .width(20.dp)
                                .height(16.dp),
                            text = "Size",
                            fontSize = 10.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF516370))

                        Text(
                            modifier = Modifier
                                .width(50.dp)
                                .height(16.dp),
                            text = viewModel.plantSize,
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            color = Color(0xFF0A6375))


                    }

                    Column(
                        modifier = Modifier
                            .width(36.dp)
                            .height(36.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            modifier = Modifier
                                .width(31.dp)
                                .height(16.dp),
                            text = "Water",
                            fontSize = 10.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF516370))

                        Text(
                            modifier = Modifier
                                .width(36.dp)
                                .height(16.dp),
                            text = viewModel.waterAmount + "ml",
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            color = Color(0xFF0A6375))

                    }
                    
                    Column(
                        modifier = Modifier
                            .width(80.dp)
                            .height(36.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            modifier = Modifier.width(53.dp).height(16.dp),
                            text = "frequency",
                            fontSize = 10.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF516370))

                        Text(
                            modifier = Modifier
                                .width(80.dp)
                                .height(16.dp),
                            text = "${viewModel.frequency} time${if (viewModel.frequency > 1) "s" else ""}/week",
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            color = Color(0xFF0A6375)
                        )



                    }
                    
                    



                }
                
            }
            

            
        }
        
        Column(modifier = Modifier
            .width(390.dp)
            .height(350.dp)
            .background(
                color = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(
                    topStart = 24.dp,
                    topEnd = 24.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp
                )
            )
            .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 20.dp)) {
            Row(modifier = Modifier
                .width(350.dp)
                .height(32.dp)) {
                Text(
                    text = viewModel.title,
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF232926))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier
                .width(349.dp)
                .height(149.dp)) {
                Text(
                    text = viewModel.description,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF516370))
            }
            Row(modifier = Modifier
                .width(350.dp)
                .height(48.dp)
                .background(color = Color(0xFF0A6375), shape = RoundedCornerShape(size = 10.dp))
                .padding(start = 20.dp,top = 12.dp, end = 20.dp, bottom = 12.dp)
                .clickable { viewModel.onEvent(DetailPlantEvent.MarkButtonClick) },
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                verticalAlignment = Alignment.Top,) {
                Text(
                    modifier = Modifier
                        .width(310.dp)
                        .height(24.dp),
                    text = "Mark as watered",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center)
            }

        }
        
    }
}