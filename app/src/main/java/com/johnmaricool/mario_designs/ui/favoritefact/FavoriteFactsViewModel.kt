package com.johnmaricool.mario_designs.ui.favoritefact

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnmaricool.mario_designs.utils.models.FactModel
import com.johnmaricool.mario_designs.utils.models.FactsFavModel
import com.johnmaricool.mario_designs.utils.repositories.FactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteFactsViewModel
@Inject constructor( var repo: FactsRepository): ViewModel() {
    private val _data = MutableLiveData<List<FactsFavModel>>()
    val data: LiveData<List<FactsFavModel>> = _data

    fun getData(){
        viewModelScope.launch(Dispatchers.IO) {
            _data.postValue(repo.allFavFacts())
        }
    }
}