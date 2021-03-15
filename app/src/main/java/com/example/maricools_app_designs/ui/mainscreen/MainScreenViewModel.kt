package com.example.maricools_app_designs.ui.mainscreen

import android.content.Context
import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import com.example.maricools_app_designs.CacheMapper
import com.example.maricools_app_designs.WorkerClass
import com.example.maricools_app_designs.database.PrayerDao
import com.example.maricools_app_designs.hilt.GetData
import com.example.maricools_app_designs.interfaces_kids.StateInterface
import com.example.maricools_app_designs.utils.models.PrayerModel
import com.example.maricools_app_designs.utils.models.PrayerServerModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

@Suppress("DEPRECATION")
class MainScreenViewModel
@ViewModelInject
    constructor(): ViewModel() {
}