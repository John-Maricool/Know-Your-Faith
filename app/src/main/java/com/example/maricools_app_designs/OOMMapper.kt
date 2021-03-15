package com.example.maricools_app_designs

import com.example.maricools_app_designs.interfaces_kids.ModelMapper
import com.example.maricools_app_designs.utils.models.*
import javax.inject.Inject

class OOMMapper
@Inject
constructor(): ModelMapper<OOMModel, OOMServerModel> {

    override fun mapFromModel(model: OOMServerModel): OOMModel {
        return OOMModel(
                oomTitle = model.title,
                oomContent = model.order
        )
    }

    override fun mapToModel(model: OOMModel): OOMServerModel {
        return OOMServerModel(model.oomTitle, model.oomContent)
    }

    fun convertToCacheList(modelList: List<OOMServerModel>): List<OOMModel>{
       return modelList.map { mapFromModel(it) }
    }
}