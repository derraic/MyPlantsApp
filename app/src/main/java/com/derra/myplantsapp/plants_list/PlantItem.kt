package com.derra.myplantsapp.plants_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.derra.myplantsapp.R
import com.derra.myplantsapp.data.Plant


@Composable
fun PlantItem(
    plant: Plant,
    onEvent: (PlantListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .width(167.dp)
            .height(196.dp)
            .padding(20.dp),
        shape = RoundedCornerShape(8.dp), // Rounded corners with a 8dp radius
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.plant_place_holder),
                    contentDescription = "Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row() {
                Column() {
                    Text(
                        text = plant.name,
                        style = TextStyle(fontSize = 14.sp, lineHeight = 20.sp)

                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = plant.description ?: "",
                        style = MaterialTheme.typography.body1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Box(contentAlignment = Alignment.Center) {
                    Image(painter = painterResource(id = R.drawable.water_marked_button), contentDescription = "Click me if watered")
                    Icon(painter = painterResource(id = R.drawable.water_icon), contentDescription = "Water Dripping")
                }

            }


        }
    }
}


