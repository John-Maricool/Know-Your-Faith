package com.johnmaricool.mario_designs.androidcomponents

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.MobileAds
import com.google.android.material.navigation.NavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.johnmaricool.mario_designs.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

     private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var prefs: SharedPreferences
     var prefsName = "MyPrefs"

    lateinit var drawer_layout: DrawerLayout
    lateinit var toolbar: Toolbar
    lateinit var nav_view: NavigationView

    @Inject
    lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        drawer_layout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        nav_view = findViewById(R.id.nav_view)

        prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE)

        appBarConfiguration = AppBarConfiguration(navController.graph, drawer_layout)
        setSupportActionBar(toolbar)
        nav_view.setupWithNavController(navController)

        val menu = nav_view.menu
        menu.findItem(R.id.log_out).setOnMenuItemClickListener {
            logOut()
        }
        menu.findItem(R.id.rate_me).setOnMenuItemClickListener {
            rateMe()
        }
        setupActionBarWithNavController(navController, appBarConfiguration)

        MobileAds.initialize(this@MainActivity)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun logOut(): Boolean {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Log Out")
        dialog.setCancelable(false)
        dialog.setMessage("Are you sure you want to exit here?")
        dialog.setPositiveButton("Yes"){_,_ ->
            finish()
        }
        dialog.setNegativeButton("No"){theDialog,_ ->
            theDialog.cancel()
        }
        val alert = dialog.create()
        alert.show()
        return true
    }
    private fun rateMe(): Boolean {
        try {
            startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$packageName")))
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=$packageName")))
        }
        return true
    }
}