package com.example.expensetracker.ui

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.activity.ComponentActivity
import com.example.expensetracker.R
import com.example.expensetracker.data.AppDatabase
import kotlinx.coroutines.*
import java.text.NumberFormat
import java.util.*

class StatisticsActivity : ComponentActivity() {
    private lateinit var spinnerMonth: Spinner
    private lateinit var spinnerYear: Spinner
    private lateinit var layoutIncome: LinearLayout
    private lateinit var layoutExpense: LinearLayout
    private lateinit var txtProfit: TextView
    private lateinit var btnFilter: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        spinnerMonth = findViewById(R.id.spinner_month)
        spinnerYear = findViewById(R.id.spinner_year)
        layoutIncome = findViewById(R.id.layout_income)
        layoutExpense = findViewById(R.id.layout_expense)
        txtProfit = findViewById(R.id.txt_profit)
        btnFilter = findViewById(R.id.btn_filter)

        val btnBack = findViewById<Button>(R.id.btn_back) // <- thêm nút back
        val months = (1..12).toList()
        val years = (2020..Calendar.getInstance().get(Calendar.YEAR)).toList().reversed()

        spinnerMonth.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, months)
        spinnerYear.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, years)

        // Mặc định chọn tháng/năm hiện tại
        spinnerMonth.setSelection(Calendar.getInstance().get(Calendar.MONTH))
        spinnerYear.setSelection(0)

        btnFilter.setOnClickListener {
            val month = spinnerMonth.selectedItem as Int
            val year = spinnerYear.selectedItem as Int
            loadData(month, year)
        }

        // Xử lý nút quay lại MainActivity
        btnBack.setOnClickListener {
            finish() // quay về MainActivity
        }
    }

    private fun loadData(month: Int, year: Int) {
        val db = AppDatabase.getDatabase(this)
        val dao = db.financeDao()
        val formatter = NumberFormat.getNumberInstance(Locale("vi", "VN"))

        CoroutineScope(Dispatchers.IO).launch {
            val allExpenses = dao.getAllExpensesList()
            val allIncomes = dao.getAllIncomesList()

            val filteredExpenses = allExpenses.filter {
                val cal = Calendar.getInstance().apply { time = Date(it.date) }
                cal.get(Calendar.MONTH) + 1 == month && cal.get(Calendar.YEAR) == year
            }

            val filteredIncomes = allIncomes.filter {
                val cal = Calendar.getInstance().apply { time = Date(it.date) }
                cal.get(Calendar.MONTH) + 1 == month && cal.get(Calendar.YEAR) == year
            }

            val totalExpense = filteredExpenses.sumOf { it.amount }
            val totalIncome = filteredIncomes.sumOf { it.amount }
            val profit = totalIncome - totalExpense

            withContext(Dispatchers.Main) {
                layoutIncome.removeAllViews()
                layoutExpense.removeAllViews()

                filteredIncomes.forEach {
                    layoutIncome.addView(createRow(it.source, it.amount))
                }

                filteredExpenses.forEach {
                    layoutExpense.addView(createRow(it.category, it.amount))
                }

                txtProfit.text = "Lợi nhuận: ${formatter.format(profit)} VND"
            }
        }
    }

    private fun createRow(label: String, amount: Double): View {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(8, 8, 8, 8)
        }

        val txtLabel = TextView(this).apply {
            text = label
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }

        val txtAmount = TextView(this).apply {
            text = NumberFormat.getNumberInstance(Locale("vi", "VN")).format(amount)
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            gravity = Gravity.END
        }

        row.addView(txtLabel)
        row.addView(txtAmount)

        return row
    }
}
