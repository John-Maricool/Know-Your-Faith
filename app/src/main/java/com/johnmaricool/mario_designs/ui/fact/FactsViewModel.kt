package com.johnmaricool.mario_designs.ui.fact

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnmaricool.mario_designs.utils.models.FactModel
import com.johnmaricool.mario_designs.utils.repositories.FactsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FactsViewModel
@ViewModelInject
constructor(var repo: FactsRepository) : ViewModel() {
    private val _data = MutableLiveData<FactModel>()
    val data: LiveData<FactModel> = _data

    fun getFact(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _data.postValue(repo.theFact(id))
        }
    }
}

