package com.example.maricools_app_designs.interfaces_kids

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem

interface OnPrayerAddedToFavourite {
    fun addToSharedPrefs(position: Int): Boolean
    fun removeFromSharedPrefs(position: Int): Boolean
}