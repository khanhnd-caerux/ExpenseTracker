package com.example.expensetracker.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.expensetracker.R
import com.example.expensetracker.adapter.OnboardingAdapter
import com.example.expensetracker.model.OnboardingItem
import com.google.android.material.button.MaterialButton

class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: OnboardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    // Nếu người dùng đã xem onboarding -> chuyển sang Login
    val sharedPref = getSharedPreferences("app_pref", MODE_PRIVATE)
    if (sharedPref.getBoolean("onboarding_done", false)) {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
        return
    }

    // Nếu chưa xem thì hiển thị màn hình onboarding
    setContentView(R.layout.activity_onboarding)

    val onboardingItems = listOf(
        OnboardingItem(R.drawable.ic_money, "Quản lý chi tiêu", "Theo dõi chi tiêu hàng ngày"),
        OnboardingItem(R.drawable.ic_chart, "Thống kê thông minh", "Xem biểu đồ chi tiêu trực quan"),
        OnboardingItem(R.drawable.ic_notification, "Nhắc nhở", "Cảnh báo khi vượt hạn mức")
    )

    viewPager = findViewById(R.id.viewPager)
    adapter = OnboardingAdapter(onboardingItems)
    viewPager.adapter = adapter

    val btnNext = findViewById<MaterialButton>(R.id.btnNext)
    btnNext.setOnClickListener {
        if (viewPager.currentItem < onboardingItems.lastIndex) {
            viewPager.currentItem += 1
        } else {
            sharedPref.edit().putBoolean("onboarding_done", true).apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
    }
}
