package com.example.remid.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.remid.data.Medicine
import com.example.remid.R
import kotlin.math.exp

@Composable
fun ExpiredMedScreen(
    expMed : List<Medicine>,
    onDone: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.more_light_gray)),
         horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "⚠️ Expired Medicines",
            color = colorResource(R.color.teal_700),
            modifier = Modifier.padding(8.dp)
        )

        LazyColumn(
           modifier = Modifier
               .fillMaxWidth()
               .weight(1f)
        ) {
             items(expMed){ med ->
                 Text(" ${med.name}", modifier = Modifier.padding(10.dp))
             }
        }

        Button(onClick = {
            onDone()
        }) {
            Text("Back")
        }
    }
}