package com.example.maricools_app_designs.adapters

import android.content.SharedPreferences
import android.util.Log
import android.view.*
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.database.FactDao
import com.example.maricools_app_designs.interfaces_kids.OnPrayerItemClickListener
import com.example.maricools_app_designs.databinding.PrayerSingleItemBinding
import com.example.maricools_app_designs.hilt.FactFav
import com.example.maricools_app_designs.utils.models.FactModel
import com.example.maricools_app_designs.utils.models.FactsFavModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.util.*
import javax.inject.Inject

class FactAdapter
@Inject
constructor(var scope: CoroutineScope,
            var pDAO: FactDao,
            @FactFav var prefs: SharedPreferences) :
    RecyclerView.Adapter<FactAdapter.MyViewHolder>(), Filterable {

    var myList: List<FactModel> = listOf()
    lateinit var listener: OnPrayerItemClickListener
    var filteredList: List<FactModel> = listOf()

    init {
        filteredList = myList
    }

    fun setOnClickListener(mlistener: OnPrayerItemClickListener){
        listener = mlistener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder{
            val binding = PrayerSingleItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    fun getFactList(list: List<FactModel>) {
        myList = list
        filteredList = myList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            if (position != RecyclerView.NO_POSITION && position < filteredList.size) {
                holder.binding?.textPrayer?.text  = filteredList[position].factTitle
            } else {
                return
            }
            val pos = filteredList[position].uid
            if (checkIfAddedToSharedPrefs(pos!!)) {
                holder.binding?.favImage?.setBackgroundResource(R.drawable.ic_red_fav)
            } else if (!checkIfAddedToSharedPrefs(pos)) {
                holder.binding?.favImage?.setBackgroundResource(R.drawable.ic_grey_fav)
            }
    }

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(sequence: CharSequence?): FilterResults {
                val charSearch = sequence.toString()
                if (charSearch.isEmpty()) {
                    filteredList = myList
                } else {
                    val resultList = mutableListOf<FactModel>()
                    for (row in myList) {
                        if (row.factTitle.toLowerCase(Locale.ROOT)
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
                filteredList = results?.values as MutableList<FactModel>
                notifyDataSetChanged()
            }
        }
    }

    fun addToSharedPrefs(position: Int, part: FactsFavModel) {
        val editor = prefs.edit()
        editor.putInt(position.toString(), position)
        scope.launch (IO){
            pDAO.addFavFact(part)
        }
        editor.apply()
    }

    fun checkIfAddedToSharedPrefs(position: Int): Boolean {
        return prefs.contains(position.toString())
    }

    fun removeFromSharedPrefs(position: Int, part: FactsFavModel) {
        val editor = prefs.edit()
        editor.remove(position.toString())
        scope.launch (IO) {
            pDAO.removeFavFact(part)
        }
        editor.apply()
    }


    @Suppress("DEPRECATION")
    inner class MyViewHolder(var binding: PrayerSingleItemBinding?) :
        RecyclerView.ViewHolder(binding?.root!!) {

        init {
            binding!!.textPrayer.setOnClickListener {

                val fact = filteredList[bindingAdapterPosition].factTitle
                val id = filteredList[bindingAdapterPosition].uid
                if (id != RecyclerView.NO_POSITION) {
                    Log.d("ONCLICK", id.toString())
                    if (id != null) {
                        listener.onPrayerItemClick(fact, id)
                    }
                }
            }

            binding!!.favImage.setOnClickListener {
                val pos = filteredList[bindingAdapterPosition].uid
                Log.d("CHECK", pos.toString())
                if (checkIfAddedToSharedPrefs(pos!!)) {
                    val partTitle = filteredList[bindingAdapterPosition].factTitle
                    val partContent = filteredList[bindingAdapterPosition].factContent
                    val favItem = FactsFavModel(pos, partTitle, partContent)
                    removeFromSharedPrefs(pos, favItem)
                    it.setBackgroundResource(R.drawable.ic_grey_fav)
                } else if (!checkIfAddedToSharedPrefs(pos)) {
                    val partTitle = filteredList[bindingAdapterPosition].factTitle
                    val partContent = filteredList[bindingAdapterPosition].factContent
                    val favItem = FactsFavModel(pos, partTitle, partContent)
                    addToSharedPrefs(pos, favItem)
                    it.setBackgroundResource(R.drawable.ic_red_fav)
                }
            }
        }
    }
}
