package com.example.maricools_app_designs.ui.fact

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.maricools_app_designs.adapters.FactsAdapter
import com.example.maricools_app_designs.adapters.FactsExpandableAdapter
import com.example.maricools_app_designs.utils.repositories.FactsListRepository

class FactsListViewModel
@ViewModelInject
constructor(var repo: FactsListRepository): ViewModel() {

    private var adapterList: MutableList<FactsAdapter> = mutableListOf()
    var adapter: FactsExpandableAdapter

     var commandment = repo.commandment
     var sacrament = repo.sacrament
     var sin_header = repo.sin
     var others = repo.others
     var prayer_header = repo.prayer
    var holy_spirit = repo.holySpirit
init {
    val commAdapter = FactsAdapter(commandment, repo.allCommandmentsTitles())
    adapterList.add(commAdapter)

    val prayAdapter = FactsAdapter(prayer_header, repo.allPrayerTitles())
    adapterList.add(prayAdapter)

    val sinAdapter = FactsAdapter(sin_header, repo.allSinTitles())
    adapterList.add(sinAdapter)

    val holyAdapter = FactsAdapter(holy_spirit, repo.allHolySpiritTitles())
    adapterList.add(holyAdapter)

    val otherAdapter = FactsAdapter(others, repo.allOtherTitles())
    adapterList.add(otherAdapter)

    val sacAdapter = FactsAdapter(sacrament, repo.allSacramentTitles())
    adapterList.add(sacAdapter)

    adapter = FactsExpandableAdapter(adapterList)
}
}
