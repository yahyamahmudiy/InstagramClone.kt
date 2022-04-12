package com.example.instagramclonekt.activity

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.instagramclonekt.R
import com.example.instagramclonekt.adapter.ViewPagerAdapter
import com.example.instagramclonekt.fragment.*
import com.google.android.material.bottomnavigation.BottomNavigationView

/*
 * It contains view pager with 5 fragments in MainActivity,
 * and pages canbe controlled by BottomNavigationView
 * */
class MainActivity : BaseActivity(), HomeFragment.HomeListener, UploadFragment.UploadListener {
    val TAG = MainActivity::class.java.simpleName
    var index = 0
    lateinit var homeFragment: HomeFragment
    lateinit var uploadFragment: UploadFragment
    lateinit var viewPager: ViewPager
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        viewPager = findViewById(R.id.viewPager)
        bottomNavigationView = findViewById(R.id.bottomNavigation)

        bottomNavigationView.setOnItemSelectedListener{ item ->
            when(item.itemId){
                R.id.navigation_home -> {
                    viewPager.setCurrentItem(0)
                }
                R.id.navigation_search -> {
                    viewPager.setCurrentItem(1)
                }
                R.id.navigation_upload -> {
                    viewPager.setCurrentItem(2)
                }
                R.id.navigation_favourite -> {
                    viewPager.setCurrentItem(3)
                }
                R.id.navigation_profile -> {
                    viewPager.setCurrentItem(4)
                }
            }
            true
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                index = position
                bottomNavigationView.getMenu().getItem(index).setChecked(true)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

        //Home and Upload Fragments are global for communication purpose
        homeFragment = HomeFragment()
        uploadFragment = UploadFragment()
        setUpViewPager(viewPager)
    }

    private fun setUpViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(homeFragment)
        adapter.addFragment(SearchFragment())
        adapter.addFragment(uploadFragment)
        adapter.addFragment(FavouriteFragment())
        adapter.addFragment(ProfileFragment())
        viewPager.adapter = adapter
    }


    private fun scrollByIndex(index: Int) {
        viewPager.setCurrentItem(index)
        bottomNavigationView.getMenu().getItem(index).setChecked(true)
    }

    override fun scrollToHome() {
        index = 0
        scrollByIndex(index)
    }

    override fun scrollToUpload() {
        index = 2
        scrollByIndex(index)
    }
}