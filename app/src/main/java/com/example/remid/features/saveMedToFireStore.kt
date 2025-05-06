package com.example.remid.features



import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

fun saveMedToFireStore(
    userId: String,
    name: String,
    dosage: String,
    timeTable: List<String>,
    expiryDate: String,
    onSuccess: () -> Unit,
    onFailure: (Exception) -> Unit
){

    val db = Firebase.firestore

    val medicine = hashMapOf(
        "userId" to userId,
        "name" to name,
        "dosage" to dosage,
        "timeTable" to timeTable,
        "expiryDate" to expiryDate
    )

    db.collection("medicines")
        .add(medicine)
        .addOnSuccessListener{
            onSuccess
        }
        .addOnFailureListener{
                e -> onFailure(e)
        }

}