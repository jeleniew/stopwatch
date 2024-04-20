package com.example.stop_watch_notify.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.stop_watch_notify.R
import com.example.stop_watch_notify.databinding.ActivityMainBinding
import com.example.stop_watch_notify.ui.view.dialog.ChooseSoundDialog
import com.example.stop_watch_notify.utils.DataProvider
import com.example.stop_watch_notify.utils.DataType
import com.example.stop_watch_notify.utils.SharedPreferencesHelper

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        SharedPreferencesHelper.initSharedPreferences(applicationContext)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_stopwatch, R.id.nav_notifications, R.id.nav_calls, R.id.nav_ringtones
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        DataProvider.init(applicationContext)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val dialogFragment = ChooseSoundDialog()
                dialogFragment.setOptions(
                    leftOptionListener = { ringtone ->
                        SharedPreferencesHelper.defaultRingtone = ringtone.id
                    },
                    rightOptionListener = null
                )
                dialogFragment.show(supportFragmentManager, "Choose default sound")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}