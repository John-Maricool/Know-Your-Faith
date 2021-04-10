package com.example.maricools_app_designs.utils.models

data class FactServerModel(var factTitle: String, var factContent: String) {
    constructor(): this("", "")
}