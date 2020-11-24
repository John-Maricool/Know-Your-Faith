package com.example.maricools_app_designs.repositories

import android.content.Context
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.adapters.FactsAdapter

class FactsListRepository(var context: Context) {

   var FactsListTitles: MutableList<String> = context.resources.getStringArray(R.array.facts_list).toMutableList()

    val adapter = FactsAdapter(FactsListTitles)
}