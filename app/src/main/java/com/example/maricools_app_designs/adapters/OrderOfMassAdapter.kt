package com.example.maricools_app_designs.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.maricools_app_designs.databinding.OrderMassSingleItemBinding
import com.example.maricools_app_designs.interfaces_kids.OnPrayerItemClickListener

class OrderOfMassAdapter(var each: List<String>): RecyclerView.Adapter<OrderOfMassAdapter.OrderOfMassViewHolder>() {

    lateinit var listener: OnPrayerItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderOfMassViewHolder {
        val binding = OrderMassSingleItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrderOfMassViewHolder(binding)
    }

    fun setOnItemClickListener(mlistener: OnPrayerItemClickListener){
        listener = mlistener
    }
    override fun getItemCount(): Int {
        return each.size
    }

    override fun onBindViewHolder(holder: OrderOfMassViewHolder, position: Int) {
      holder.binding.textPrayer.text =  each[position]
    }

    inner class OrderOfMassViewHolder(var binding: OrderMassSingleItemBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.textPrayer.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION){
                    listener.onPrayerItemClick(binding.textPrayer.text.toString(), bindingAdapterPosition)
                }
            }
        }
    }

}