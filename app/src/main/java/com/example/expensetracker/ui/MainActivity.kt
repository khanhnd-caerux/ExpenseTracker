package com.example.expensetracker.ui

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import com.example.expensetracker.R
import android.content.Intent
import com.example.expensetracker.data.AppDatabase
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.*
import java.util.*

class MainActivity : ComponentActivity() {
    private lateinit var chart: BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnAddIncome).setOnClickListener {
            startActivity(Intent(this, AddIncomeActivity::class.java))
        }

        findViewById<Button>(R.id.btnAddExpense).setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        findViewById<Button>(R.id.btnStatistics).setOnClickListener {
            startActivity(Intent(this, StatisticsActivity::class.java))
        }

        chart = findViewById(R.id.chart_bar)
    }

    override fun onResume() {
        super.onResume()
        loadChartData() // Gọi lại mỗi khi quay về MainActivity
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
                    Color.RED                    // hoặc Color.parseColor("#FF0000")
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
