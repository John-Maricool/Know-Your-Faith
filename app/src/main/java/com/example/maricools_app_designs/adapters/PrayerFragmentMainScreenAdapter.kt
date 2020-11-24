package com.example.maricools_app_designs.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.maricools_app_designs.MainActivity
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.`interface`.onPrayerItemClickListener
import com.example.maricools_app_designs.databinding.PrayerHeaderBinding
import com.example.maricools_app_designs.databinding.PrayerSingleItemBinding
import java.util.*

class PrayerFragmentMainScreenAdapter(val header: String, var myList: MutableList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private val TYPE_HEADER: Int = 0
    private val TYPE_ITEM: Int = 1

    var filteredList: MutableList<String> = mutableListOf()

    init {
        filteredList = myList
    }

    lateinit var itemClickListener: onPrayerItemClickListener


    fun setOnItemClickListener(listener: onPrayerItemClickListener) {
        this.itemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
            val binding = PrayerHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return MyHeaderViewHolder(binding)

        } else if (viewType == TYPE_ITEM) {
            val binding = PrayerSingleItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            return MyViewHolder(binding)
        }
        return MyViewHolder(null)
    }

    override fun getItemCount(): Int {
        return filteredList.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyHeaderViewHolder) {
            val Theholder: MyHeaderViewHolder = holder
            Theholder.binding!!.prayerHeader.text = header
        } else if (holder is MyViewHolder) {
            val Theholder: MyViewHolder = holder

            if (position-1 != RecyclerView.NO_POSITION || position-1 != filteredList.size) {
                Theholder.binding?.textPrayer?.text = filteredList[position - 1]
            } else {
                return
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_HEADER
        } else {
            TYPE_ITEM
        }
    }

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(sequence: CharSequence?): FilterResults {
                val charSearch = sequence.toString()
                if (charSearch.isEmpty()) {
                    filteredList = myList
                } else {
                    val resultList = mutableListOf<String>()
                    for (row in myList) {
                        if (row.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    filteredList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(sequence: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as MutableList<String>
               notifyDataSetChanged()
            }

        }
    }

    @Suppress("DEPRECATION")
     inner class MyViewHolder(var binding: PrayerSingleItemBinding?) :
        RecyclerView.ViewHolder(binding?.root!!) {

        var isAddedToFav: Boolean = false

        init{
          /*  binding!!.fav.setOnClickListener {
                if (!isAddedToFav) {
                    val prayer = filteredList[bindingAdapterPosition]
                    binding!!.fav.setImageResource(R.drawable.ic_red_favorite_24)
                    itemClickListener.onFavoriteItem(prayer)
                    isAddedToFav = true

                } else {
                    binding!!.fav.setImageResource(R.drawable.ic_baseline_favorite_24)
                    isAddedToFav = false
                }
            }*/


            binding!!.textPrayer.setOnClickListener {

                val text = binding?.textPrayer?.text
                val size = myList.size
                var position = 0

                for (i in 0 until size){
                    if (text == myList[i]){
                        position = i
                        break
                    }
                    else{
                        continue
                    }
                }

                val prayer = filteredList[bindingAdapterPosition - 1]
                if (position != RecyclerView.NO_POSITION || position > RecyclerView.NO_POSITION) {
                    itemClickListener.onPrayerItemClick(prayer, position)
                }
            }
        }
    }

    inner class MyHeaderViewHolder(var binding: PrayerHeaderBinding?) :
        RecyclerView.ViewHolder(binding!!.root) {
    }
}