package com.example.expensetracker.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.expensetracker.R
import com.example.expensetracker.data.AppDatabase
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    private lateinit var chart: BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Xin quyền thông báo nếu là Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }

        // Lấy FCM Token và log ra
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("FCM_TOKEN", "Lỗi khi lấy FCM token", task.exception)
                    return@addOnCompleteListener
                }

                val token = task.result
                Log.d("FCM_TOKEN", "FCM Token: $token")
                // Có thể gửi token này lên server nếu cần
            }

        // Gắn sự kiện các nút
        findViewById<Button>(R.id.btnAddIncome).setOnClickListener {
            startActivity(Intent(this, AddIncomeActivity::class.java))
        }

        findViewById<Button>(R.id.btnAddExpense).setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        findViewById<Button>(R.id.btnStatistics).setOnClickListener {
            startActivity(Intent(this, StatisticsActivity::class.java))
        }

        // Tìm biểu đồ
        chart = findViewById(R.id.chart_bar)
    }

    override fun onResume() {
        super.onResume()
        loadChartData() // Tải lại dữ liệu mỗi khi quay lại
    }

    private fun loadChartData() {
        val db = AppDatabase.getDatabase(this)
        val dao = db.financeDao()

        CoroutineScope(Dispatchers.IO).launch {
            val incomes = dao.getAllIncomesList()
            val expenses = dao.getAllExpensesList()

            val totalIncome = incomes.sumOf { it.amount }
            val totalExpense = expenses.sumOf { it.amount }

            withContext(Dispatchers.Main) {
                val entries = listOf(
                    BarEntry(0f, totalIncome.toFloat()),
                    BarEntry(1f, totalExpense.toFloat())
                )

                val dataSet = BarDataSet(entries, "Thống kê")
                dataSet.colors = listOf(
                    Color.parseColor("#018786"), // teal_700
                    Color.RED
                )
                dataSet.valueTextSize = 14f

                val barData = BarData(dataSet)
                barData.barWidth = 0.4f

                chart.data = barData
                chart.xAxis.apply {
                    valueFormatter = IndexAxisValueFormatter(listOf("Doanh thu", "Chi phí"))
                    granularity = 1f
                    position = XAxis.XAxisPosition.BOTTOM
                    setDrawGridLines(false)
                }

                chart.axisRight.isEnabled = false
                chart.description.isEnabled = false
                chart.setFitBars(true)
                chart.animateY(1000)
                chart.invalidate()
            }
        }
    }
}
