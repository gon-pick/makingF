package com.example.making_f.Activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.making_f.R
import com.example.making_f.navigation.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.action_home ->{
                var detailViewFragment = HomeFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, detailViewFragment).commit()
                return true
            }
            R.id.action_search ->{
                var gridFragment = SearchFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, gridFragment).commit()
                return true
            }
            R.id.action_favorite_alarm ->{
                var alarmFragment = PostFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, alarmFragment).commit()
                return true
            }
            R.id.action_account ->{
                var userFragment = UserFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, userFragment).commit()
                return true
            }
            R.id.action_myboard ->{
                var boardFragment = myboardfragment()
                var bundle = Bundle()
                var uid = FirebaseAuth.getInstance().currentUser?.uid
                bundle.putString("destinationUid",uid)
                boardFragment.arguments=bundle
                supportFragmentManager.beginTransaction().replace(R.id.main_content,boardFragment).commit()
                return true
            }
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var detailViewFragment = HomeFragment()
        supportFragmentManager.beginTransaction().replace(R.id.main_content, detailViewFragment).commit()
        bottom_navigation.setOnNavigationItemSelectedListener(this)
    }



}