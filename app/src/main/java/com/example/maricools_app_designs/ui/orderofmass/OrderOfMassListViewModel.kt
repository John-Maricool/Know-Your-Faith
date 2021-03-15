package com.example.maricools_app_designs.ui.orderofmass

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.adapters.OrderOfMassAdapter


class OrderOfMassListViewModel
@ViewModelInject
constructor(application: Application): AndroidViewModel(application) {
    var list : List<String> = application.resources.getStringArray(R.array.order_of_mass).toList()
    val adapter = OrderOfMassAdapter(list)
}