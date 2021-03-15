package com.example.maricools_app_designs.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.maricools_app_designs.utils.models.PrayerFavModel
import com.example.maricools_app_designs.databinding.FragmentCustomSingleItemsBinding
import com.example.maricools_app_designs.interfaces_kids.OnPrayerItemClickListener
import javax.inject.Inject

class FavouritePrayerAdapter
    
@Inject
    constructor()
    : RecyclerView.Adapter<FavouritePrayerAdapter.FavPrayerViewHolder>() {

    var each: MutableList<PrayerFavModel> = mutableListOf()

    lateinit var listener: OnPrayerItemClickListener

    fun setOnClickListener(mlistener: OnPrayerItemClickListener){
        listener = mlistener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavPrayerViewHolder {
        val binding = FragmentCustomSingleItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavPrayerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return each.size
    }

    fun getAllFavPrayers(items: MutableList<PrayerFavModel>){
        each = items
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: FavPrayerViewHolder, position: Int) {
      holder.binding.customItemText.text =  each[position].prayerTitle
    }

    inner class FavPrayerViewHolder(var binding: FragmentCustomSingleItemsBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.customItemText.setOnClickListener {
                val prayer = each[bindingAdapterPosition].prayerTitle
                val id = each[bindingAdapterPosition].uid
                if (id != RecyclerView.NO_POSITION) {
                    Log.d("ONCLICK", id.toString())
                    listener.onPrayerItemClick(prayer, id)
                }
            }
        }
    }

}