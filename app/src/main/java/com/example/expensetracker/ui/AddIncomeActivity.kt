package com.example.expensetracker.ui

import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.R
import com.example.expensetracker.data.AppDatabase
import com.example.expensetracker.data.Income
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*
import com.example.expensetracker.data.FinanceDao


class AddIncomeActivity : ComponentActivity() {
    private lateinit var adapter: IncomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_income)

        val editAmount = findViewById<EditText>(R.id.edit_income_amount)
        val spinnerSource = findViewById<Spinner>(R.id.spinner_source)
        val btnSave = findViewById<Button>(R.id.btn_save_income)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_income)

        val db = AppDatabase.getDatabase(this)
        val incomeDao = db.financeDao()

        adapter = IncomeAdapter(mutableListOf()) { incomeToDelete ->
            lifecycleScope.launch {
                incomeDao.deleteIncome(incomeToDelete)
                loadIncomes(incomeDao)
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        loadIncomes(incomeDao)

        btnSave.setOnClickListener {
            val amountText = editAmount.text.toString()
            val amount = amountText.toDoubleOrNull()
            val source = spinnerSource.selectedItem.toString()

            if (amount != null && amount > 0) {
                lifecycleScope.launch {
                    incomeDao.insertIncome(Income(source = source, amount = amount))
                    loadIncomes(incomeDao)
                    runOnUiThread {
                        Toast.makeText(this@AddIncomeActivity, "Đã lưu doanh thu", Toast.LENGTH_SHORT).show()
                        editAmount.text.clear()
                    }
                }
            } else {
                Toast.makeText(this, "Vui lòng nhập số tiền hợp lệ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadIncomes(incomeDao: FinanceDao) {
        lifecycleScope.launch {
            val allIncomes = incomeDao.getAllIncomesList()
            val total = allIncomes.sumOf { it.amount }
            val formatter = NumberFormat.getNumberInstance(Locale("vi", "VN"))
            runOnUiThread {
                adapter.updateData(allIncomes)
                val txtTotal = findViewById<TextView>(R.id.txt_total_income)
                txtTotal.text = "Tổng doanh thu: ${formatter.format(total)} VND"
            }
        }
    }
}
