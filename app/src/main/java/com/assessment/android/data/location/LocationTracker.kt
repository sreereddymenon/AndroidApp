package com.assessment.android.data.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}