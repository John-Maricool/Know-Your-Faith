package com.example.maricools_app_designs.repositories

import android.content.Context
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.adapters.PrayerFragmentMainScreenAdapter

class PrayerListRepository(context: Context) {

    val adapter2: PrayerFragmentMainScreenAdapter
    val adapter3: PrayerFragmentMainScreenAdapter
    val adapter4: PrayerFragmentMainScreenAdapter
    val adapter1: PrayerFragmentMainScreenAdapter
    lateinit var listOfPrayers: MutableList<String>
    lateinit var listOfMysteries: MutableList<String>
    lateinit var saints_contents: MutableList<String>
    lateinit var jesus_contents: MutableList<String>


    init {
        val header: String = context.resources.getString(R.string.kids_prayer)
       listOfPrayers = context.resources.getStringArray(R.array.under_topics).toMutableList()
        adapter2 = PrayerFragmentMainScreenAdapter(header, listOfPrayers)

        val rosary_header: String = context.resources.getString(R.string.the_rosary)
       listOfMysteries = context.resources.getStringArray(R.array.rosary_topics).toMutableList()
        adapter3 = PrayerFragmentMainScreenAdapter(rosary_header, listOfMysteries)

        val saints_header: String = context.resources.getString(R.string.saints_prayer)
        saints_contents = context.resources.getStringArray(R.array.saints_prayers).toMutableList()
        adapter4 = PrayerFragmentMainScreenAdapter(
            saints_header,
            saints_contents
        )

        val jesus_header: String = context.resources.getString(R.string.jesus_prayer)
        jesus_contents = context.resources.getStringArray(R.array.jesus_prayer).toMutableList()
        adapter1 = PrayerFragmentMainScreenAdapter(jesus_header, jesus_contents)
    }
}