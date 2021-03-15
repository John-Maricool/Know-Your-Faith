package com.example.maricools_app_designs.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.maricools_app_designs.utils.models.FactModel
import com.example.maricools_app_designs.databinding.ExpandableBodyBinding
import com.example.maricools_app_designs.databinding.ExpandableHeaderBinding
import com.example.maricools_app_designs.interfaces_kids.OnItemClickListener
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class FactsExpandableAdapter(groups: MutableList<FactsAdapter>):
    ExpandableRecyclerViewAdapter<FactGroupViewHolder, FactViewHolder>(groups) {

    lateinit var listener: OnItemClickListener
    override fun onCreateGroupViewHolder(parent: ViewGroup?, viewType: Int): FactGroupViewHolder {
        val binding = ExpandableHeaderBinding.inflate(
            LayoutInflater.from(parent?.context),
            parent,
            false
        )
        return FactGroupViewHolder(binding)
    }

    override fun onCreateChildViewHolder(parent: ViewGroup?, viewType: Int): FactViewHolder {
        val binding = ExpandableBodyBinding.inflate(
            LayoutInflater.from(parent?.context),
            parent,
            false
        )
        return FactViewHolder(binding)
    }

    fun setOnClickListener(mlistener: OnItemClickListener){
        listener = mlistener
    }

    override fun onBindChildViewHolder(
            holder: FactViewHolder?,
            flatPosition: Int,
            group: ExpandableGroup<*>?,
            childIndex: Int
    ) {
        val model = group?.items?.get(childIndex)
        val pos = holder?.bind(model as FactModel)

        holder?.binding?.factTitle?.setOnClickListener {
            if (pos != null) {
                listener.onItemClick(pos)
            }
        }
    }

    override fun onBindGroupViewHolder(
            holder: FactGroupViewHolder?,
            flatPosition: Int,
            group: ExpandableGroup<*>?
    ) {
        val model = group as FactsAdapter
        holder?.bind(model)
    }
}