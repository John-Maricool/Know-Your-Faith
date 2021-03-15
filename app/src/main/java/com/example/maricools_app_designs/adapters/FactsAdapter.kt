package com.example.maricools_app_designs.adapters

import com.example.maricools_app_designs.databinding.ExpandableBodyBinding
import com.example.maricools_app_designs.databinding.ExpandableHeaderBinding
import com.example.maricools_app_designs.utils.models.FactModel
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder

class FactsAdapter
constructor(title: String?, factItem: MutableList<FactModel>):
  ExpandableGroup<FactModel>(title, factItem)

   class FactGroupViewHolder(var binding: ExpandableHeaderBinding): GroupViewHolder(binding.root){
     fun bind(name: FactsAdapter){
       binding.factHeader.text = name.title
     }
  }

   class FactViewHolder(var binding: ExpandableBodyBinding): ChildViewHolder(binding.root){
    fun bind(model: FactModel): Int{
      binding.factTitle.text = model.factTitle
           val pos =  model.uid
        return pos!!
    }


  }