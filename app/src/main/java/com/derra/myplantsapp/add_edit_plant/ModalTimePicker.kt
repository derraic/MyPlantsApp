package com.derra.myplantsapp.add_edit_plant


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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

private suspend fun LazyListState.scrollToIndex(index: Int) {
    animateScrollToItem(index)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ModalTime(
    modifier: Modifier,
    viewModel: AddEditPlantViewModel,


    ) {
    val radioButtonColors = RadioButtonDefaults.colors(
        selectedColor = colorResource(id = R.color.AccentA500)
        // Customize the color when checked
        // = BorderStroke(2.dp, Color.Black) // Customize the border color and stroke width
    )


    if (viewModel.modalTime) {
        AlertDialog(
            onDismissRequest = { viewModel.onEvent(AddEditPlantEvent.CancelButtonClick) },
            text = {
                val hours = (0..23).toList()
                val minutes = (0..59).toList()
                Column(modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()) {
                    val currTime = viewModel.currTime
                    val (selectedHour, selectedMinute) = currTime.split(":")
                    val hourIndex = hours.indexOf(selectedHour.toInt())
                    val minuteIndex = minutes.indexOf(selectedMinute.toInt())
                    Row(modifier = Modifier.width(310.dp)) {
                        Text(text = "Time",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF232926))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier
                        .height(186.dp)
                        .fillMaxWidth()
                    ) {
                        Column {
                            for (i in 0..37) {
                                if (i == 5 || i == 14 || i == 23 || i == 32) {
                                    Spacer(
                                        modifier = Modifier
                                            .width(44.dp)
                                            .height(1.dp)
                                            .background(color = Color(0xFF9CA4AB))
                                    )
                                }
                                else {
                                    Spacer(
                                        modifier = Modifier
                                            .width(13.dp)
                                            .height(1.dp)
                                            .background(color = Color(0xFF9CA4AB))
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        PickerList(
                            items = hours.map { it.toString() },
                            selectedIndex = hourIndex + hours.size,
                            onItemSelected = { index -> viewModel.onEvent(AddEditPlantEvent.OnTimeChange("${hours[index % hours.size].toString().padStart(2, '0')}:${selectedMinute.padStart(2,'0')}"))},
                            hours = true
                        )
                        PickerList(
                            items = minutes.map { it.toString() },
                            selectedIndex = minuteIndex + minutes.size,
                            onItemSelected = { index -> viewModel.onEvent(AddEditPlantEvent.OnTimeChange("${selectedHour.padStart(2, '0')}:${minutes[index % minutes.size].toString().padStart(2,'0')}")) }
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column (horizontalAlignment = Alignment.End) {
                            for (i in 0..37) {
                                if (i == 5 || i == 14 || i == 23 || i == 32) {
                                    Spacer(
                                        modifier = Modifier
                                            .width(44.dp)
                                            .height(1.dp)
                                            .background(color = Color(0xFF9CA4AB))
                                    )
                                }
                                else {
                                    Spacer(
                                        modifier = Modifier
                                            .width(13.dp)
                                            .height(1.dp)
                                            .background(color = Color(0xFF9CA4AB))
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                        }


                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            //.offset(x = 0.dp, y = 496.dp)
                            .width(350.dp)
                            .height(88.dp)
                            .padding(top = 16.dp, bottom = 24.dp),
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
                                textAlign = TextAlign.Center
                            )
                        }
                        Box(
                            modifier = Modifier
                                //.background(color = Color(0xFF0A6375), shape = RoundedCornerShape(size = 10.dp))
                                //.padding(start = 20.dp, top = 12.dp, end = 20.dp, bottom = 12.dp)
                                .width(151.dp)
                                .height(48.dp)
                                .background(
                                    color = Color(0xFF0A6375),
                                    shape = RoundedCornerShape(size = 10.dp)
                                )
                                .padding(start = 20.dp, top = 12.dp, end = 20.dp, bottom = 12.dp)
                                .clickable { viewModel.onEvent(AddEditPlantEvent.SaveButtonTimeClick) }
                            //.paint(painter = painterResource(id = R.drawable.button_got_it_good))
                        ) {
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
                                textAlign = TextAlign.Center
                            )
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
                .height(360.dp)
                .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 16.dp))

        )
    }

}

@Composable
fun PickerList(
    items: List<String>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    hours: Boolean = false
) {
    val scrollState: LazyListState =
        rememberLazyListState(initialFirstVisibleItemIndex = selectedIndex - 2)
    val loopingItems = remember(items, scrollState.layoutInfo.totalItemsCount) {
        val repeatFactor = 3 // Number of times to repeat the items
        val repeatedItems = List(items.size * repeatFactor) { index ->
            items[index % items.size]
        }
        repeatedItems
    }

    //val adjustedSelectedIndex = remember(selectedIndex, loopingItems) {
    //    selectedIndex + items.size // Adjust the selected index to ensure it starts in the middle of the list
    //}

    LazyColumn(
        state = scrollState, modifier = Modifier
    ) {
        itemsIndexed(loopingItems) { index, item ->
            val adjustedIndex = index % items.size + items.size
            val isSelected = adjustedIndex == selectedIndex
            Row(modifier = Modifier
                .height(if (!isSelected) 37.dp else 45.dp)) {
                Text(
                    text = item.padStart(2, '0'),
                    modifier = Modifier
                        .width(50.dp)
                        .height(if (!isSelected) 37.dp else 45.dp),
                    fontSize = if (!isSelected) 34.sp else 40.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.sfrprodisplayregular)),
                    fontWeight = FontWeight(300),
                    color = if (isSelected) Color(0xFF111827) else Color(0xFF718096)
                )
                if (hours) {
                    Spacer(modifier = Modifier.width(33.dp))
                    Text(
                        text = ":",
                        modifier = Modifier
                            .width(9.dp)
                            .height(45.dp),
                        fontSize = if (!isSelected) 34.sp else 40.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.sfrprodisplayregular)),
                        fontWeight = FontWeight(300),
                        color = if (isSelected) Color(0xFF111827) else Color(0xFF718096)
                    )
                    Spacer(modifier = Modifier.width(38.dp))
                }


            }

        }
    }
    //var scrolling by remember { mutableStateOf(false) }

    LaunchedEffect(scrollState.isScrollInProgress) {
        if (!scrollState.isScrollInProgress) {
            val firstVisibleIndex = scrollState.firstVisibleItemIndex
            val centerIndex = firstVisibleIndex + scrollState.layoutInfo.visibleItemsInfo.size / 2
            val adjustedIndex = centerIndex % items.size
            if (centerIndex < items.size || centerIndex > items.size * 2  ) {
                scrollState.scrollToItem(adjustedIndex + items.size - 2)
            }
            //scrolling = true // Set scrolling to true to indicate that the scroll is initiated
            //scrollState.animateScrollToItem(adjustedIndex + items.size)
            onItemSelected(adjustedIndex + items.size)
            //scrollState.animateScrollToItem(adjustedIndex + items.size)
        } //else if (!scrollState.isScrollInProgress && scrolling) {
        //    scrolling = false // Set scrolling back to false after the scroll is completed
        //}
    }


    //LaunchedEffect(selectedIndex) {
    //    scrollState.animateScrollToItem(selectedIndex)
    //    onItemSelected(selectedIndex)
    //} //
}

