package com.example.expensetracker.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.example.expensetracker.R

open class BaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView

    override fun setContentView(layoutResID: Int) {
        // Dùng layout với drawer
        val fullView = layoutInflater.inflate(R.layout.activity_with_drawer, null)
        val activityContainer = fullView.findViewById<FrameLayout>(R.id.activityContent)
        layoutInflater.inflate(layoutResID, activityContainer, true)
        super.setContentView(fullView)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        setSupportActionBar(findViewById(R.id.toolbar))

        toggle = ActionBarDrawerToggle(
            this, drawerLayout, findViewById(R.id.toolbar),
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_main -> {
                if (this !is MainActivity) {
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
            R.id.nav_user_info -> {
                if (this !is UserInfoActivity) {
                    startActivity(Intent(this, UserInfoActivity::class.java))
                }
            }
            R.id.nav_logout -> {
                Toast.makeText(this, "Đăng xuất", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
        drawerLayout.closeDrawers()
        return true
    }
}
