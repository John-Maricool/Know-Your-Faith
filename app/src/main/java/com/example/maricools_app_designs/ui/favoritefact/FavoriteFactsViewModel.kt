package com.example.maricools_app_designs.ui.favoritefact

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.maricools_app_designs.utils.models.FactsFavModel
import com.example.maricools_app_designs.utils.repositories.FavFactRepository

class FavoriteFactsViewModel
@ViewModelInject
constructor( var repo: FavFactRepository): ViewModel() {

    fun getFavFacts(): LiveData<MutableList<FactsFavModel>> {
        return repo.getFavFacts()
    }
}