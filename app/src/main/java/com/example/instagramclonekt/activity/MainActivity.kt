package com.example.instagramclonekt.activity

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import com.example.instagramclonekt.R
import com.example.instagramclonekt.adapter.ViewPagerAdapter
import com.example.instagramclonekt.fragment.*
import com.google.android.material.bottomnavigation.BottomNavigationView

/*
 * It contains view pager with 5 fragments in MainActivity,
 * and pages canbe controlled by BottomNavigationView
 * */
class MainActivity : BaseActivity() {
    val TAG = MainActivity::class.java.simpleName
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        bottomNavigationView = findViewById(R.id.bottomNavigation)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.homeFragment -> {
                    bottomNavigationView.isVisible = true
                }
                R.id.searchFragment -> {
                    bottomNavigationView.isVisible = true
                }
                R.id.uploadFragment -> {
                    bottomNavigationView.isVisible = true
                }
                R.id.favouriteFragment -> {
                    bottomNavigationView.isVisible = true
                }
                R.id.profileFragment -> {
                    bottomNavigationView.isVisible = true
                }
                R.id.likedPostsFragment -> {
                    bottomNavigationView.isVisible = false
                }
            }
        }
    }
}