package com.example.maricools_app_designs.ui.orderofmass

import android.R.string
import android.content.Context
import android.os.Build
import android.text.Html
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.maricools_app_designs.utils.models.OOMModel
import com.example.maricools_app_designs.utils.repositories.OOMRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.StringBuilder

class OOMViewModel
    @ViewModelInject
    constructor(@ApplicationContext val context: Context, var repo: OOMRepository)
    : ViewModel() {

    fun getOOM(title: String): String {
      val inputStream = context.assets.open("MY FILES/$title.txt")
       val size: Int = inputStream.available()
       val buffer = ByteArray(size)
       inputStream.read(buffer)
           val string =  Html.fromHtml(String(buffer), 0)
       return string.toString()
    }
}