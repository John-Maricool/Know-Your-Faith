package com.example.maricools_app_designs.ui.fact

import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.maricools_app_designs.hilt.FactFav
import com.example.maricools_app_designs.utils.models.FactModel
import com.example.maricools_app_designs.utils.repositories.FactsRepository
import com.example.maricools_app_designs.utils.models.FactsFavModel

class FactsViewModel
@ViewModelInject
constructor(var repo: FactsRepository) : ViewModel() {

    fun getParticularFact(id: Int): FactModel {
        return repo.theFact(id)
    }
}

