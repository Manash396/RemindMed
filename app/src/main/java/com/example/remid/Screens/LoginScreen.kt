package com.example.remid.Screens

import android.graphics.Color
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.remid.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    navController: NavController
){
    val auth = FirebaseAuth.getInstance()
    val email =  remember { mutableStateOf("") }
    val password =  remember { mutableStateOf("") }
    val context = LocalContext.current



    if (auth.currentUser != null) {
        navController.navigate("LoginToMain"){
            popUpTo("login"){
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.more_light_gray))
            .statusBarsPadding()
            .navigationBarsPadding()

    ){



        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
                .padding(5.dp)

        ) {


            Row(
             modifier = Modifier
                 .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Spacer(modifier = Modifier.width(20.dp))
                Image(
                    painter = painterResource(R.drawable.icon_remi),
                    contentDescription = "App logo",
                    modifier = Modifier.size(60.dp)
                )
                Spacer(modifier = Modifier.width(30.dp))
                Text(
                    text = "Welcome Back",
                    fontSize = 25.sp,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = colorResource(R.color.teal_200)
                    )
                )

            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email") },
                colors = TextFieldDefaults.colors(
                cursorColor = colorResource(R.color.light_blue),
                    focusedTextColor =colorResource(R.color.light_blue),         // Text color when focused
                    unfocusedTextColor = colorResource(R.color.light_gray),    // Text color when not focused
                    focusedLeadingIconColor = colorResource(R.color.light_blue),   // Icon color when focused
                    unfocusedLeadingIconColor = colorResource(R.color.light_gray), // Icon color when not focused
                    focusedLabelColor = colorResource(R.color.light_blue),
                    unfocusedLabelColor =colorResource(R.color.light_gray),
                    focusedContainerColor = colorResource(R.color.more_light_gray),     // ✅ For focused state
                    unfocusedContainerColor = colorResource(R.color.more_light_gray)
                ),
                leadingIcon = { Icon(Icons.Default.Email,contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Password") },
                leadingIcon = {Icon(Icons.Default.Lock,contentDescription = null)},
                colors = TextFieldDefaults.colors(
                    cursorColor = colorResource(R.color.light_blue),
                    focusedTextColor =colorResource(R.color.light_blue),         // Text color when focused
                    unfocusedTextColor = colorResource(R.color.light_gray),    // Text color when not focused
                    focusedLeadingIconColor = colorResource(R.color.light_blue),   // Icon color when focused
                    unfocusedLeadingIconColor = colorResource(R.color.light_gray), // Icon color when not focused
                    focusedLabelColor = colorResource(R.color.light_blue),
                    unfocusedLabelColor =colorResource(R.color.light_gray),
                    focusedContainerColor = colorResource(R.color.more_light_gray),     // ✅ For focused state
                    unfocusedContainerColor = colorResource(R.color.more_light_gray)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { //
                 if (email.value.toString() != "" && password.value.toString() != ""){
                    auth.signInWithEmailAndPassword(
                        email.value.toString(),
                        password.value.toString()
                    )
                        .addOnSuccessListener {
                            navController.navigate("LoginToMain") {
                                popUpTo("login") {
                                    inclusive = true
                                }
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "User Id not found", Toast.LENGTH_LONG).show()
                            navController.navigate("register") {
                                popUpTo("login") {
                                    inclusive = true
                                }
                            }

                        }
                    //
                }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login", color = colorResource(R.color.teal_700))
            }
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("Don't have an account?", color = colorResource(R.color.teal_200))
                TextButton(onClick = {navController.navigate("LoginToRegister"){
                    popUpTo("login"){
                        inclusive = true
                    }
                }

                }) {
                    Text("Sign Up",color = colorResource(R.color.teal_700))
                }
            }

        }

    }
}


