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
constructor(var repo: FactsRepository, @FactFav var prefs: SharedPreferences) : ViewModel() {

    fun getParticularFact(id: Int): FactModel {
        return repo.theFact(id)
    }

    private fun addFact(fact: FactsFavModel){
        repo.addFavFact(fact)
    }

    private fun removeFact(fact: FactsFavModel){
        repo.removeFavFact(fact)
    }

    fun addToSharedPrefs(position: Int, part: FactsFavModel) {
        val editor = prefs.edit()
        editor.putInt(position.toString(), position)
       addFact(part)
        editor.apply()
    }

    fun checkIfAddedToSharedPrefs(position: Int): Boolean {
        return prefs.contains(position.toString())
    }

    fun removeFromSharedPrefs(position: Int, part: FactsFavModel) {
        val editor = prefs.edit()
        editor.remove(position.toString())
        removeFact(part)
        editor.apply()
    }
}