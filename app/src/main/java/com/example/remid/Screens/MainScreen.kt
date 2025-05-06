package com.example.remid.Screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.remid.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.remid.data.Medicine
import com.example.remid.features.delMedFromFireStore
import com.example.remid.features.getMedFromDb
import com.example.remid.features.saveMedToFireStore
import com.example.remid.services.AlarmScheduler
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.delay
import java.util.Calendar
import kotlin.math.sign


@Composable
fun MainScreen(
    navController: NavController
){

    var medicines by remember { mutableStateOf(listOf<Medicine>())  }
    var expiredMed by remember {  mutableStateOf(listOf<Medicine>())  }

    var showDialog by remember { mutableStateOf(false) }

    var showExpMed by remember { mutableStateOf(true) }

    val userId = Firebase.auth.currentUser?.uid

    var showMedicine by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    var medicineId by remember { mutableStateOf("") }


    val context = LocalContext.current

    LaunchedEffect(Unit) {
        userId?.let {
            getMedFromDb(
                it,
                onResult = { med ->
                    medicines = med
                }
            )
        }
    }




    if (showMedicine) {
          MedicineDisplayScreen(medicineId, navController) {
              showMedicine = false
          }
          return
      }

      if (showLogoutDialog) {
          LogoutDialog(
              onRefresh = {
                  navController.navigate("spin")
                  showLogoutDialog = false
              },
              onSignout = {
                  showLogoutDialog = false
                  Firebase.auth.signOut()
                  navController.navigate("MainToLogin") {
                      popUpTo("main") {
                          inclusive = true
                      }
                  }
              }
          )
          return
      }



      if (showDialog) {
          AddMedicineScreen(
              navController,
              onDismiss = { showDialog = false },
              onSave = { name, dosage, time, expDate ->
                  val userid = Firebase.auth.currentUser?.uid ?: return@AddMedicineScreen
                  showDialog = false
                  saveMedToFireStore(
                      userid,
                      name, dosage, time, expDate,
                      onSuccess = {
                          Toast.makeText(context, "Medicine saved!", Toast.LENGTH_SHORT).show()
                      },
                      onFailure = { e ->
                          Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                      }
                  )
                  userId?.let {
                      getMedFromDb(
                          it,
                          onResult = { med ->
                              medicines = med
                          }
                      )
                  }
              }
          )
      }



      Column(
          modifier = Modifier
              .fillMaxSize()
              .background(colorResource(R.color.more_light_gray))
              .statusBarsPadding()
              .navigationBarsPadding(),
          horizontalAlignment = Alignment.CenterHorizontally
      ) {

          Image(
              painter = painterResource(R.drawable.icon_remi),
              contentDescription = "App logo",
              modifier = Modifier.size(100.dp)
                  .clickable {
                      showLogoutDialog = true
                  }
          )

          Spacer(modifier = Modifier.height(16.dp))

          LazyColumn(
              modifier = Modifier
                  .weight(1f)
                  .fillMaxWidth()
          ) {

              items(medicines, key = { it.userId }) { medicine ->
                  var sch = AlarmScheduler(context)
                  sch.schedule(medicine)
                  val parts = medicine.expiryDate.split("-") // expects format "yyyy-MM-dd"
                  val year = parts[0].toInt()
                  val month = parts[1].toInt()
                  val day = parts[2].toInt()

                  val today = java.util.Calendar.getInstance()
                  val expiry = java.util.Calendar.getInstance().apply {
                      set(year, month - 1, day) // month is 0-based
                  }
                  Card(
                      modifier = Modifier
                          .fillMaxWidth()
                          .padding(vertical = 10.dp)
                          .padding(horizontal = 15.dp)
                          .clickable {
                              medicineId = medicine.userId
                              showMedicine = true
                          },
                      colors = CardDefaults.cardColors(

                          containerColor = if (expiry.before(today)) {colorResource(R.color.danger)}
                                  else{colorResource(R.color.dark_greenish_blu)},
                          contentColor = colorResource(R.color.more_light_gray)
                      ),
                      elevation = CardDefaults.cardElevation(90.dp)
                  ) {
                      Text(
                          "Medicine: ${medicine.name}",
                          modifier = Modifier.padding(5.dp).padding(horizontal = 10.dp)
                      )
                      Text(
                          "Dosage: ${medicine.dosage}",
                          modifier = Modifier.padding(5.dp).padding(horizontal = 10.dp)
                      )
                      Text(
                          "Time: ${medicine.timeTable} ",
                          modifier = Modifier.padding(5.dp).padding(horizontal = 10.dp)
                      )
                      Text(
                          "ExpDate: ${medicine.expiryDate}",
                          modifier = Modifier.padding(5.dp).padding(horizontal = 10.dp)
                      )
                  }
              }

          }

          Button(
              modifier = Modifier
                  .padding(10.dp),
              onClick = {
                  showDialog = true
              }
          ) {
              Text("Add Remid")
          }

      }
  }

