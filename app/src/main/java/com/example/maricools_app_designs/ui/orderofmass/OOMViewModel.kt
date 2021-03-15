package com.example.maricools_app_designs.ui.orderofmass

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.maricools_app_designs.utils.models.OOMModel
import com.example.maricools_app_designs.utils.repositories.OOMRepository

class OOMViewModel
    @ViewModelInject
    constructor(var repo: OOMRepository)
    : ViewModel() {

   private fun getOOM(title: String): OOMModel {
        return repo.OOMPart(title)
    }
    fun getStringTitle(title: String): String{
       return getOOM(title).oomTitle
    }
    fun getStringContent(title: String): String{
        return getOOM(title).oomContent
    }

}