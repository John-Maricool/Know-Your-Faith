package com.example.maricools_app_designs.ui.orderofmass

import android.content.Context
import android.text.Html
import androidx.core.text.HtmlCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

class OOMViewModel
    @ViewModelInject
    constructor(@ApplicationContext val context: Context)
    : ViewModel() {

    fun getOOM(title: String): String {
      val inputStream = context.assets.open("MY FILES/$title.txt")
       val size: Int = inputStream.available()
       val buffer = ByteArray(size)
       inputStream.read(buffer)
           val string =  HtmlCompat.fromHtml(String(buffer),  HtmlCompat.FROM_HTML_MODE_LEGACY)
       return string.toString()
    }
}