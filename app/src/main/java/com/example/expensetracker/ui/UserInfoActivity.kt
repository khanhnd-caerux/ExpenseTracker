package com.example.expensetracker.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.expensetracker.data.AppDatabase
import com.example.expensetracker.data.User
import com.example.expensetracker.R
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserInfoActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        val db = AppDatabase.getDatabase(this)
        val userDao = db.financeDao()

        // Truy vấn và cập nhật UI trong coroutine
        lifecycleScope.launch {
            var user = withContext(Dispatchers.IO) {
                userDao.getUser()
            }

            if (user == null) {
                user = User(
                    id = 0,
                    fullName = "Nguyễn Văn A",
                    email = "vana@example.com",
                    phone = "0123456789",
                    birthDate = "01/01/1990"
                )

                // Insert user vào DB trong coroutine
                withContext(Dispatchers.IO) {
                    userDao.insertUser(user)
                }
            }

            // Gán dữ liệu vào UI (chạy trên Main thread)
            findViewById<TextView>(R.id.tvFullName).text = user.fullName
            findViewById<TextView>(R.id.tvEmail).text = user.email
            findViewById<TextView>(R.id.tvPhone).text = user.phone
            findViewById<TextView>(R.id.tvBirthDate).text = user.birthDate
            findViewById<ImageView>(R.id.user_avatar).setImageResource(R.drawable.ic_user_placeholder)
        }
    }
}
