package com.johnmaricool.mario_designs.ui.orderofmass

import android.content.Context
import android.text.Html
import androidx.core.text.HtmlCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class OOMViewModel
  @Inject constructor(@ApplicationContext var context: Context)
    : ViewModel() {

    fun getOOM(title: String): String {
      val inputStream = context.assets.open("MY FILES/$title.html")
       val size: Int = inputStream.available()
       val buffer = ByteArray(size)
       inputStream.read(buffer)
           val string =  HtmlCompat.fromHtml(String(buffer), 0)
       return string.toString()
    }
}