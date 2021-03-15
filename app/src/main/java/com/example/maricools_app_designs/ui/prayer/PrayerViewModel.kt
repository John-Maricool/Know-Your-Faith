package com.example.maricools_app_designs.ui.prayer

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import com.example.maricools_app_designs.adapters.PrayerViewPagerAdapter
import com.example.maricools_app_designs.utils.models.PrayerModel
import com.example.maricools_app_designs.utils.repositories.PrayerRepository

class PrayerViewModel
@ViewModelInject
constructor(application: Application, var repository: PrayerRepository) : AndroidViewModel(application){
    val adapter: PrayerViewPagerAdapter = PrayerViewPagerAdapter(repository.getAllPrayers())
}