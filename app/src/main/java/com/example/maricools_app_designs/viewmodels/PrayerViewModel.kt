package com.example.maricools_app_designs.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.maricools_app_designs.repositories.PrayerRepository

class PrayerViewModel(application: Application) : AndroidViewModel(application){

    val repository = PrayerRepository(application)

    val adapter = repository.adapter

}