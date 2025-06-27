package com.example.expensetracker.ui

import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.R
import com.example.expensetracker.data.AppDatabase
import com.example.expensetracker.data.Expense
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*
import com.example.expensetracker.data.FinanceDao

class AddExpenseActivity : ComponentActivity() {
    private lateinit var adapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        // Ánh xạ view
        val editAmount = findViewById<EditText>(R.id.edit_expense_amount)
        val spinnerCategory = findViewById<Spinner>(R.id.spinner_category)
        val btnSave = findViewById<Button>(R.id.btn_save_expense)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_expense)
        val btnBack = findViewById<Button>(R.id.btn_back) // <- thêm nút back

        // Khởi tạo DB và DAO
        val db = AppDatabase.getDatabase(this)
        val expenseDao = db.financeDao()

        // Adapter và danh sách chi tiêu
        adapter = ExpenseAdapter(mutableListOf()) { expenseToDelete ->
            lifecycleScope.launch {
                expenseDao.deleteExpense(expenseToDelete)
                loadExpenses(expenseDao)
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Load dữ liệu ban đầu
        loadExpenses(expenseDao)

        // Lưu chi tiêu mới
        btnSave.setOnClickListener {
            val amountText = editAmount.text.toString()
            val amount = amountText.toDoubleOrNull()
            val category = spinnerCategory.selectedItem.toString()

            if (amount != null && amount > 0) {
                lifecycleScope.launch {
                    expenseDao.insertExpense(Expense(category = category, amount = amount))
                    loadExpenses(expenseDao)
                    runOnUiThread {
                        Toast.makeText(this@AddExpenseActivity, "Đã lưu chi tiêu", Toast.LENGTH_SHORT).show()
                        editAmount.text.clear()
                    }
                }
            } else {
                Toast.makeText(this, "Vui lòng nhập số tiền hợp lệ", Toast.LENGTH_SHORT).show()
            }
        }

        // Xử lý nút quay lại MainActivity
        btnBack.setOnClickListener {
            finish() // đóng AddExpenseActivity, quay về MainActivity
        }
    }

    private fun loadExpenses(expenseDao: FinanceDao) {
        lifecycleScope.launch {
            val allExpenses = expenseDao.getAllExpensesList()
            val total = allExpenses.sumOf { it.amount }
            val formatter = NumberFormat.getNumberInstance(Locale("vi", "VN"))
            runOnUiThread {
                adapter.updateData(allExpenses)
                val txtTotal = findViewById<TextView>(R.id.txt_total_expense)
                txtTotal.text = "Tổng chi tiêu: ${formatter.format(total)} VND"
            }
        }
    }
}
