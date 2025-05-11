package com.example.remid.data

import java.time.LocalDate

data class Medicine(
    val userId : String,
    val name   : String,
    val dosage : String,
    val timeTable : List<String>,
    val expiryDate : String,
    var medId : String
){
    // for firebase need this
   constructor() : this(
       userId = "",
       name = "",
       dosage = "",
       timeTable = emptyList<String>(),
       expiryDate = "",
       medId = ""
   )
}

