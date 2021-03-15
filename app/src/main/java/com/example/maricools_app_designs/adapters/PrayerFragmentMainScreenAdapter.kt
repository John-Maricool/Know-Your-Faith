package com.example.maricools_app_designs.adapters

import android.content.SharedPreferences
import android.util.Log
import android.view.*
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.maricools_app_designs.hilt.Fav
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.database.PrayerDao
import com.example.maricools_app_designs.utils.models.PrayerFavModel
import com.example.maricools_app_designs.interfaces_kids.OnPrayerItemClickListener
import com.example.maricools_app_designs.databinding.PrayerHeaderBinding
import com.example.maricools_app_designs.databinding.PrayerSingleItemBinding
import com.example.maricools_app_designs.utils.models.PrayerModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.util.*
import javax.inject.Inject

class PrayerFragmentMainScreenAdapter
@Inject
constructor(var scope: CoroutineScope,
            var pDAO: PrayerDao,
            @Fav var prefs: SharedPreferences) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private val TYPE_HEADER: Int = 0
    private val TYPE_ITEM: Int = 1
    var header: String = "prayer"
    var myList: List<PrayerModel> = listOf()
    lateinit var listener: OnPrayerItemClickListener
    var filteredList: List<PrayerModel> = listOf()

    init {
        filteredList = myList
    }

    fun setOnClickListener(mlistener: OnPrayerItemClickListener){
        listener = mlistener
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
        return if (filteredList.size == 0) {
            1
        } else {
            filteredList.size + 1
        }
    }

    fun getPrayerList(list: List<PrayerModel>) {
        myList = list
        filteredList = myList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyHeaderViewHolder) {
            val Theholder: MyHeaderViewHolder = holder
            Theholder.binding!!.prayerHeader.text = header
        } else if (holder is MyViewHolder) {
            val Theholder: MyViewHolder = holder

            val newPos = position - 1
            if (newPos != RecyclerView.NO_POSITION && newPos < filteredList.size) {
                Theholder.binding?.textPrayer?.text = filteredList[newPos].prayerTitle
            } else {
                return
            }
            val pos = filteredList[newPos].uid.minus(1)
            if (checkIfAddedToSharedPrefs(pos)) {
                Theholder.binding?.favImage?.setBackgroundResource(R.drawable.ic_red_fav)
            } else if (!checkIfAddedToSharedPrefs(pos)) {
                Theholder.binding?.favImage?.setBackgroundResource(R.drawable.ic_grey_fav)
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
                    val resultList = mutableListOf<PrayerModel>()
                    for (row in myList) {
                        if (row.prayerTitle.toLowerCase(Locale.ROOT)
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
                filteredList = results?.values as MutableList<PrayerModel>
                notifyDataSetChanged()
            }
        }
    }

    fun addToSharedPrefs(position: Int, part: PrayerFavModel) {
        val editor = prefs.edit()
        editor.putInt(position.toString(), position)
        scope.launch (IO){
            pDAO.addFavPrayer(part)
        }
        editor.apply()
    }

    fun checkIfAddedToSharedPrefs(position: Int): Boolean {
        return prefs.contains(position.toString())
    }

    fun removeFromSharedPrefs(position: Int, part: PrayerFavModel) {
        val editor = prefs.edit()
        editor.remove(position.toString())
        scope.launch (IO) {
            pDAO.removeFavPrayer(part)
        }
        editor.apply()
    }


    @Suppress("DEPRECATION")
    inner class MyViewHolder(var binding: PrayerSingleItemBinding?) :
        RecyclerView.ViewHolder(binding?.root!!) {

        init {
            binding!!.textPrayer.setOnClickListener {

                val prayer = filteredList[bindingAdapterPosition - 1].prayerTitle
                val id = filteredList[bindingAdapterPosition - 1].uid.minus(1)
                if (id != RecyclerView.NO_POSITION) {
                    Log.d("ONCLICK", id.toString())
                    listener.onPrayerItemClick(prayer, id)
                }
            }

            binding!!.favImage.setOnClickListener {
                val pos = filteredList[bindingAdapterPosition - 1].uid.minus(1)
                Log.d("CHECK", pos.toString())
                if (checkIfAddedToSharedPrefs(pos)) {
                    val partTitle = filteredList[bindingAdapterPosition - 1].prayerTitle
                    val partContent = filteredList[bindingAdapterPosition - 1].prayerContent
                    val favItem = PrayerFavModel(pos, partTitle, partContent)
                    removeFromSharedPrefs(pos, favItem)
                    it.setBackgroundResource(R.drawable.ic_grey_fav)
                } else if (!checkIfAddedToSharedPrefs(pos)) {
                    val partTitle = filteredList[bindingAdapterPosition - 1].prayerTitle
                    val partContent = filteredList[bindingAdapterPosition - 1].prayerContent
                    val favItem = PrayerFavModel(pos, partTitle, partContent)
                    addToSharedPrefs(pos, favItem)
                    it.setBackgroundResource(R.drawable.ic_red_fav)
                }
            }
        }
    }

    inner class MyHeaderViewHolder(var binding: PrayerHeaderBinding?) :
        RecyclerView.ViewHolder(binding!!.root) {
    }
}
