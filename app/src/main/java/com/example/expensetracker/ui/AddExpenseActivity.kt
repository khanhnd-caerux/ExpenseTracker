package com.example.expensetracker.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.expensetracker.R
import com.example.expensetracker.data.AppDatabase
import com.example.expensetracker.data.Expense
import kotlinx.coroutines.launch
import android.widget.Spinner
import androidx.activity.ComponentActivity


class AddExpenseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        val editAmount = findViewById<EditText>(R.id.edit_expense_amount)
        val spinnerCategory = findViewById<Spinner>(R.id.spinner_category)
        val btnSave = findViewById<Button>(R.id.btn_save_expense)

        val db = AppDatabase.getDatabase(this)
        val expenseDao = db.financeDao()

        btnSave.setOnClickListener {
            val amountText = editAmount.text.toString()
            val amount = amountText.toDoubleOrNull()
            val category = spinnerCategory.selectedItem.toString()

            if (amount != null && amount > 0) {
                lifecycleScope.launch {
                    expenseDao.insertExpense(Expense(category = category, amount = amount))
                    runOnUiThread {
                        Toast.makeText(this@AddExpenseActivity, "Đã lưu chi tiêu", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            } else {
                Toast.makeText(this, "Vui lòng nhập số tiền hợp lệ", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
