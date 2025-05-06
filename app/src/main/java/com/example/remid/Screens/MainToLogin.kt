package com.example.remid.Screens





import android.os.Message
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.remid.R
import kotlinx.coroutines.delay


@Composable
fun MainToLoginSpin(
    navController: NavController,
){
    var loading by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.more_light_gray))
        ,
        contentAlignment = Alignment.Center
    ){
        if (loading){
            CircularProgressIndicator(modifier = Modifier.size(50.dp))
        }else{
            navController.navigate("login"){
                popUpTo("MainToLogin"){
                    inclusive = true
                }
            }
        }

        LaunchedEffect(Unit) {
            delay(3000)
            loading = false
        }
    }
}