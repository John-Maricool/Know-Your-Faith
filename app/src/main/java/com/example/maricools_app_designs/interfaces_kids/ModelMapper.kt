package com.example.maricools_app_designs.interfaces_kids

interface ModelMapper<CacheModel, ServerModel> {

    fun mapFromModel(model: ServerModel): CacheModel
    fun mapToModel(model: CacheModel): ServerModel
}