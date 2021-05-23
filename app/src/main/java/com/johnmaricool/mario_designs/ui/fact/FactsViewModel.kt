package com.johnmaricool.mario_designs.ui.fact

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.johnmaricool.mario_designs.utils.models.FactModel
import com.johnmaricool.mario_designs.utils.repositories.FactsRepository

class FactsViewModel
@ViewModelInject
constructor(var repo: FactsRepository) : ViewModel() {

    fun getParticularFact(id: Int): FactModel {
        return repo.theFact(id)
    }
}

