package com.example.maricools_app_designs.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.`interface`.onPrayerItemClickListener
import com.example.maricools_app_designs.databinding.FactsSingleItemBinding
import com.example.maricools_app_designs.databinding.MainScreenSingleItemLayoutBinding
import kotlinx.android.synthetic.main.fragment_prayer.view.*
import java.util.*

class FactsAdapter(val factsTitles: MutableList<String>) :
    RecyclerView.Adapter<FactsAdapter.FactsViewHolder>(), Filterable {

    lateinit var itemClickListener: onPrayerItemClickListener

    var filteredList: MutableList<String> = mutableListOf()

    init {
        filteredList = factsTitles
    }

    fun setOnItemClickListener(listener: onPrayerItemClickListener) {
        this.itemClickListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactsViewHolder {
        val binding = FactsSingleItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)

            return FactsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FactsViewHolder, position: Int) {
       holder.binding?.fact?.text = filteredList[position]
    }

    override fun getItemCount(): Int {
     return filteredList.size
    }

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(sequence: CharSequence?): FilterResults {
                val charSearch = sequence.toString()
                filteredList = if (charSearch.isEmpty()) {
                    factsTitles
                } else {
                    val resultList = mutableListOf<String>()
                    for (row in factsTitles) {
                        if (row.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
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

    inner class FactsViewHolder(val binding: FactsSingleItemBinding?) :
        RecyclerView.ViewHolder(binding?.root!!) {

        init {
            binding?.fact?.setOnClickListener {
                var position = 0
                val text = binding.fact.text
                val size = factsTitles.size

                for (i in 0 until size){
                    if (text == factsTitles[i]){
                        position = i
                        break
                    }
                    else{
                        continue
                    }
                }
                itemClickListener.onPrayerItemClick(it.text.toString(), position)
            }
        }
    }

}