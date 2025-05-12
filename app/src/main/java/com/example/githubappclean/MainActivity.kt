package com.example.githubappclean

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.githubappclean.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        applicationContext.apply {
            setSupportActionBar(binding.myToolbar)
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.container) as NavHostFragment

            navController = navHostFragment.navController
            NavigationUI.setupWithNavController(binding.myToolbar, navController)

            navController = navHostFragment.navController
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.mainFragment -> {
                        supportActionBar?.setDisplayShowTitleEnabled(false)
                        binding.githubLogo.visibility = View.VISIBLE
                    }
                    R.id.searchFragment -> {
                        binding.githubLogo.visibility = View.GONE
                        showToolbarWithTitle(getString(R.string.search))
                        supportActionBar?.setDisplayShowTitleEnabled(true)
                    }
                    else -> showToolbarWithTitle(destination.label?.toString() ?: "")
                }
            }
        }
    }

    private fun showToolbarWithTitle(title: String) {
        supportActionBar?.apply {
            setDisplayShowCustomEnabled(false)
            setDisplayShowTitleEnabled(true)
            this.title = title
        }
    }

    companion object {
        const val PARCEL_LOGIN = "parcel_login"
        const val PARCEL_ID = "parcel_id"
    }
}