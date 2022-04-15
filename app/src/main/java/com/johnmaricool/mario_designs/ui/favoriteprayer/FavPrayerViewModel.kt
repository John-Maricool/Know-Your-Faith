package com.johnmaricool.mario_designs.ui.favoriteprayer

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnmaricool.mario_designs.utils.models.PrayerFavModel
import com.johnmaricool.mario_designs.utils.repositories.PrayerListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavPrayerViewModel
@Inject constructor(var repo: PrayerListRepository): ViewModel() {

    private val _data = MutableLiveData<List<PrayerFavModel>>()
    val data: LiveData<List<PrayerFavModel>> = _data

    fun getData() {
        viewModelScope.launch(IO) {
            _data.postValue(repo.getFavPrayers())
        }
    }
}