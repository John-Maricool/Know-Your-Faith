package com.example.maricools_app_designs.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.interfaces_kids.OnItemClickListener
import com.example.maricools_app_designs.databinding.MainScreenSingleItemLayoutBinding
import com.example.maricools_app_designs.utils.models.MainScreenRecyclerModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MainScreenRecyclerAdapter
@Inject
constructor(@ApplicationContext val context: Context?) :
    RecyclerView.Adapter<MainScreenRecyclerAdapter.MyViewHolder>() {
    private val myList: MutableList<MainScreenRecyclerModel> = mutableListOf()

    lateinit var itemClickListener: OnItemClickListener
    init {
        myList.add(
                MainScreenRecyclerModel(
                        context!!.resources.getString(R.string.prayers),
                        context.resources.getString(R.string.prayers_desc),
                        "https://images.unsplash.com/photo-1579215176023-00341ea5ea67?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=700&q=500"
                )
        )
        myList.add(
                MainScreenRecyclerModel(
                        context.resources.getString(R.string.facts),
                        context.resources.getString(R.string.facts_desc),
                        "https://images.unsplash.com/photo-1476461386254-61c4ff3a1cc3?ixlib=rb-1.2.1&auto=format&fit=crop&w=700&q=500"
                )
        )
        myList.add(
                MainScreenRecyclerModel(
                        context.resources.getString(R.string.quiz),
                        context.resources.getString(R.string.quiz_desc),
                        "https://images.unsplash.com/photo-1530462943125-677cc511c87e?ixlib=rb-1.2.1&auto=format&fit=crop&w=700&q=500"
                )
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = MainScreenSingleItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.itemClickListener = listener
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val mListPos = myList.get(position)
        Glide.with(holder.itemView.context)
            .load(mListPos.imageRes)
            .centerCrop()
            .placeholder(R.drawable.for_view)
            .into(holder.binding.imageView)

        holder.binding.apply {
            title.text = mListPos.title
            titleDescription.text = mListPos.description
        }
    }

    inner class MyViewHolder(val binding: MainScreenSingleItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemClick(position)
                }
            }
        }
    }

}