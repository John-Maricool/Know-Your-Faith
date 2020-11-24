package com.example.maricools_app_designs.viewModels

import android.app.Application
import android.widget.SearchView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ConcatAdapter
import com.example.maricools_app_designs.repositories.PrayerListRepository
import com.example.maricools_app_designs.repositories.PrayerRepository

class PrayerListViewModel(application: Application) : AndroidViewModel(application) {

    val repo: PrayerListRepository = PrayerListRepository(application)
    val adapter1 = repo.adapter1
    val adapter2 = repo.adapter2
    val adapter3 = repo.adapter3
    val adapter4 = repo.adapter4

    val adapter1Prayers = repo.listOfPrayers


    var concat: ConcatAdapter = ConcatAdapter(adapter1, adapter2, adapter3, adapter4)





}