package com.example.maricools_app_designs.ui.favoriteprayer

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.maricools_app_designs.utils.models.PrayerFavModel
import com.example.maricools_app_designs.utils.repositories.FavRepository

class FavPrayerViewModel
@ViewModelInject
constructor(var repo: FavRepository): ViewModel() {

    fun getFavPrayers(): LiveData<MutableList<PrayerFavModel>> {
        return repo.getFavPrayers()
    }
}