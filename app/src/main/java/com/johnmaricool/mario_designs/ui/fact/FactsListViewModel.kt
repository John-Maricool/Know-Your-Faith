package com.johnmaricool.mario_designs.ui.fact

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnmaricool.mario_designs.database.FactDao
import com.johnmaricool.mario_designs.utils.models.FactModel
import com.johnmaricool.mario_designs.utils.repositories.FactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactsListViewModel
@Inject constructor(var repo: FactsRepository): ViewModel() {

    private val _data = MutableLiveData<List<FactModel>>()
    val data: LiveData<List<FactModel>> = _data

    fun getData(){
        viewModelScope.launch(IO) {
            _data.postValue(repo.allFacts())
        }
    }
}
