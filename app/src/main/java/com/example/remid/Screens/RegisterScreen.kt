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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.remid.R
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RegisterScreen(
    navController: NavController
){
    val user = remember { mutableStateOf("") }
    val email =  remember { mutableStateOf("") }
    val password =  remember { mutableStateOf("") }
    val context = LocalContext.current

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
                Spacer(modifier = Modifier.width(25.dp))
                Text(
                    text = "Create an Account",
                    fontSize = 25.sp,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = colorResource(R.color.teal_200)
                    )
                )

            }

            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = user.value,
                onValueChange = {user.value = it},
                label = {Text("UserName")},
                leadingIcon = {Icon(Icons.Default.Person,contentDescription = null)},
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

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Default.Email,contentDescription = null) },
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
                onClick = {
                    if (email.value.toString() != "" && password.value.toString() != ""){
                    val auth = FirebaseAuth.getInstance()
                    auth.createUserWithEmailAndPassword(
                        email.value.toString(),
                        password.value.toString()
                    )
                        .addOnSuccessListener{

                            Toast.makeText(context,"Account Created",Toast.LENGTH_LONG).show()
                            navController.navigate("LoginToMain"){
                                popUpTo("register"){
                                    inclusive = true
                                }
                            }
                        }
                }},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register", color = colorResource(R.color.teal_700))
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("Have an account?", color = colorResource(R.color.teal_200))
                TextButton(onClick =  {navController.navigate("RegisterToLogin"){
                    popUpTo("register"){
                        inclusive = true
                    }
                }

                }) {
                    Text("Sign In",color = colorResource(R.color.teal_700))
                }
            }

        }

    }
}


