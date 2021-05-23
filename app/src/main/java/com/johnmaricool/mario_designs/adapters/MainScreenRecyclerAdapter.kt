package com.johnmaricool.mario_designs.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johnmaricool.mario_designs.R
import com.johnmaricool.mario_designs.databinding.MainScreenSingleItemLayoutBinding
import com.johnmaricool.mario_designs.interfaces_kids.OnItemClickListener
import com.johnmaricool.mario_designs.utils.models.MainScreenRecyclerModel
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
                        context.getDrawable(R.drawable.catholicprayer)
                )
        )
        myList.add(
                MainScreenRecyclerModel(
                        context.resources.getString(R.string.facts),
                        context.resources.getString(R.string.facts_desc),
                        context.getDrawable(R.drawable.catholicfacts)
                )
        )
        myList.add(
                MainScreenRecyclerModel(
                        context.resources.getString(R.string.quiz),
                        context.resources.getString(R.string.quiz_desc),
                        context.getDrawable(R.drawable.catholicquiz)
                )
        )
        myList.add(
                MainScreenRecyclerModel(
                        context.resources.getString(R.string.oom_name),
                        context.resources.getString(R.string.oom_desc),
                        context.getDrawable(R.drawable.catholicoom)
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
        holder.binding.imageView.setImageDrawable(mListPos.imageRes)

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