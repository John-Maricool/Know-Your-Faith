package com.johnmaricool.mario_designs.adapters

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johnmaricool.mario_designs.databinding.PrayerItemViewpagerBinding
import com.johnmaricool.mario_designs.utils.models.PrayerModel

class PrayerViewPagerAdapter(val prayer: List<PrayerModel>): RecyclerView.Adapter<PrayerViewPagerAdapter.ViewPagerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding = PrayerItemViewpagerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewPagerViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return prayer.size
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.binding.prayerTitle.text = prayer[position].prayerTitle
       val text =  prayer[position].prayerContent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.binding.prayerBody.text = Html.fromHtml(text, 0)
        }
    }

    inner class ViewPagerViewHolder(var binding: PrayerItemViewpagerBinding): RecyclerView.ViewHolder(binding.root) {

    }
}