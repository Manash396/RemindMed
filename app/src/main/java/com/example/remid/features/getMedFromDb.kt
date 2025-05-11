package com.example.remid.features

import com.example.remid.data.Medicine
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

fun getMedFromDb(
    userId : String,
    onResult: (List<Medicine>) -> Unit
){
    Firebase.firestore.collection("medicines")
        .whereEqualTo("userId",userId)
        .get()
        .addOnSuccessListener{
            snapshot ->
             val medicine =  snapshot.documents.mapNotNull { doc ->
               val m =  doc.toObject(Medicine::class.java)
                   m?.copy(medId = doc.id)
             }
            onResult(medicine)
        }

}