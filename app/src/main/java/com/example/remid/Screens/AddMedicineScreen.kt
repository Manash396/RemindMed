package com.example.remid.Screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AddMedicineScreen(
    navController: NavController,
    onDismiss: () -> Unit,
    onSave:  (String, String, List<String>, String) -> Unit
){
    var name by remember {mutableStateOf("")}
    var dosage by remember {mutableStateOf("")}
    var time by remember {mutableStateOf(listOf<String>())}
    var expDate by remember {mutableStateOf("")}

    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Remid") },
        text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = name,
                    onValueChange ={ name =  it},
                    label ={ Text("Medicine Name")}
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = dosage,
                    onValueChange ={ dosage =  it},
                    label ={ Text("Dosage")}
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                          val cal  = Calendar.getInstance()
                        TimePickerDialog(
                            context,
                            {
                                _,hour,minute ->
                                 val formated = String.format("%02d:%02d",hour,minute)
                                 time = time + formated
                            },
                            cal.get(Calendar.HOUR_OF_DAY),
                            cal.get(Calendar.MINUTE)
                            ,true
                        ).show()
                    }
                ) {
                    Text("Add Time ($time)")
                }

                    Spacer(modifier = Modifier.height(10.dp))

                Button(onClick =
                {
                    val cal  = Calendar.getInstance()
                    DatePickerDialog(
                        context,
                        {
                            _, year,month,day ->
                             expDate = String.format("%04d-%02d-%02d",year,month+1,day)
                        },
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
                ) {
                    Text(if (expDate.isEmpty())"Add ExpDate" else "Expiry : $expDate")
                }


            }
        },
        confirmButton = {
            Button(onClick = {
                   if(name.isNotBlank() && dosage.isNotBlank() && expDate.isNotBlank() && time.isNotEmpty()){
                       onSave(name,dosage,time,expDate)
                      navController.navigate("spin")
                   }


            }) {
                Text("Save")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }

    )
}