package com.example.maricools_app_designs.utils.models

data class PrayerServerModel(val title: String, val prayer: String, val part: String, val id: Int) {
    constructor(): this("", "", "", 0)
}