package com.example.remid.features

import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

fun delMedFromFireStore(
    medicineId: String
){

    Firebase.firestore.collection("medicines")
        .document(medicineId)
        .delete()
        .addOnSuccessListener{

        }
        .addOnFailureListener{ e ->

        }
}