package com.example.remid.Screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.remid.data.Medicine
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.example.remid.R
import com.example.remid.services.AlarmScheduler

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineDisplayScreen(
    medicine: Medicine?,
    navController: NavController,
    onResult: () -> Unit
){

    val context = LocalContext.current



    Scaffold(
         topBar = {  TopAppBar(
            title = { Text("       Medicine Details") },
            navigationIcon = {
                IconButton(
                    onClick = {
                          onResult()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "back"
                    )
                }
            }
        )
         }
    ) {
        padding ->
           if (medicine == null ){
               Box(
                   modifier = Modifier
                       .fillMaxSize()
                       .padding(padding),
                   contentAlignment = Alignment.Center
               ){
                   Text("Medicine not Found")
               }
           }else{
               Column(
                   modifier = Modifier
                       .fillMaxSize()
                       .padding(20.dp)
                       .padding(padding),
                   horizontalAlignment = Alignment.CenterHorizontally,
                   verticalArrangement = Arrangement.spacedBy(16.dp)
               ) {
                   Card (
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(vertical = 10.dp)
                           .padding(horizontal = 15.dp)
                         ,
                       colors = CardDefaults.cardColors(
                           containerColor =  colorResource(R.color.dark_greenish_blu),
                           contentColor = colorResource(R.color.more_light_gray)
                       ),
                       elevation = CardDefaults.cardElevation(90.dp)

                   ) {
                       Text(
                           "Medicine Name: ${medicine!!.name}", modifier = Modifier.padding(20.dp),
                           style = MaterialTheme.typography.headlineSmall
                       )
                       Text(
                           "Dosage: ${medicine!!.dosage}", modifier = Modifier.padding(20.dp),
                           style = MaterialTheme.typography.bodyLarge
                       )
                       Text(
                           "Time: ${medicine!!.timeTable.joinToString()}", modifier = Modifier.padding(20.dp),
                           style = MaterialTheme.typography.bodyLarge
                       )
                       Text(
                           "Expires: ${medicine!!.expiryDate}", modifier = Modifier.padding(20.dp),
                           style = MaterialTheme.typography.bodyLarge
                       )

                       Text(
                           "Id: ${medicine!!.userId}",  modifier = Modifier.padding(20.dp),
                           style = MaterialTheme.typography.bodyLarge
                       )
                   }
                   Spacer(modifier = Modifier.weight(1f))

                   Button(
                       onClick = {
                           val scheduler = AlarmScheduler(context)
                           scheduler.unschedule(medicine)
                           Firebase.firestore.collection("medicines")
                               .document(medicine.medId)
                               .delete()
                               .addOnSuccessListener{
                                   navController.navigate("spin")
                                   Toast.makeText(context, "Deleted", Toast.LENGTH_LONG).show()
                                   onResult()
                               }
                               .addOnFailureListener{ e ->
                                   Toast.makeText(context, "Delete failed: ${e.message}", Toast.LENGTH_SHORT).show()
                               }
                       }
                   ) {
                       Text("Delete Medicine", color = MaterialTheme.colorScheme.onError)
                   }
               }
           }

    }

}