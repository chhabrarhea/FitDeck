package com.example.workoutapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import android.content.Intent
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.workoutapp.R
import com.example.workoutapp.databinding.ActivityMainBinding
import com.example.workoutapp.other.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var   navHostFragment:NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.inflate(layoutInflater,R.layout.activity_main,null,false)
        setContentView(binding.root)

        navHostFragment = supportFragmentManager
                .findFragmentById(R.id.NavHost) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        navController
                .addOnDestinationChangedListener { _, destination, _ ->
                    when(destination.id) {
                        R.id.profileFragment, R.id.homeFragment, R.id.statisticsFragment,R.id.historyFragment ->
                            binding.bottomNavigationView.visibility = View.VISIBLE
                        else -> binding.bottomNavigationView.visibility = View.GONE
                    }
                }


    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToTrackingFragmentIfNeeded(intent)
    }

    private fun navigateToTrackingFragmentIfNeeded(intent: Intent?) {
        if(intent?.action == ACTION_SHOW_TRACKING_FRAGMENT) {
            navHostFragment.findNavController().navigate(R.id.action_global_trackingFragment)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController=navHostFragment.navController
        return navController.navigateUp() || super.onSupportNavigateUp()

    }


}