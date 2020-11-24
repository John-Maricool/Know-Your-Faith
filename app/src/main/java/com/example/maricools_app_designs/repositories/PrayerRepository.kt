package com.example.maricools_app_designs.repositories

import android.content.Context
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.adapters.PrayerViewPagerAdapter

class PrayerRepository(val context: Context) {

    val prayerTitles: MutableList<String> = context.resources.getStringArray(R.array.list_of_prayer_title).toMutableList()
    val prayers: MutableList<String> = context.resources.getStringArray(R.array.list_of_prayers).toMutableList()

    val adapter = PrayerViewPagerAdapter(prayerTitles, prayers)

}