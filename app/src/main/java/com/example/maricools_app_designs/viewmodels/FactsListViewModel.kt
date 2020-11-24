package com.example.maricools_app_designs.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.maricools_app_designs.repositories.FactsListRepository

class FactsListViewModel(application: Application): AndroidViewModel(application) {

    lateinit var repo: FactsListRepository

    init {
        repo = FactsListRepository(application)
    }

    var adapter = repo.adapter
}