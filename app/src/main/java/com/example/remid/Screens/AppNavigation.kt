package com.example.remid.Screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login"){LoginScreen(navController)}
        composable("register") {RegisterScreen(navController)}
        composable("main") {MainScreen(navController)}
        composable("spin") {SpinnerScreen(navController)}
        composable("LoginToMain") {LoginToMainSpin(navController)}
        composable("MainToLogin") {MainToLoginSpin(navController)}
        composable("LoginToRegister"){LoginToRegisterSpin(navController)}
        composable("RegisterToLogin"){RegisterToLoginSpin(navController)}

    }
}