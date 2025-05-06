package com.example.remid.services

import com.example.remid.data.Medicine

interface Scheduler {
    fun schedule(medicine: Medicine)
}