package com.example.maricools_app_designs.ui.fact

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.maricools_app_designs.database.FactDao

class FactsListViewModel
@ViewModelInject
constructor(var dao: FactDao): ViewModel() {
    var facts = dao.getAllFacts()
}
