package com.example.maricools_app_designs.utils.models

data class FactServerModel(var title: String, var fact: String, var part: String) {
    constructor(): this("", "", "")
}