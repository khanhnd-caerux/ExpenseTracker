package com.example.expensetracker.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.expensetracker.R
import com.example.expensetracker.data.AppDatabase
import com.example.expensetracker.data.Income
import kotlinx.coroutines.launch
import android.widget.Spinner
import androidx.activity.ComponentActivity


class AddIncomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_income)

        val editAmount = findViewById<EditText>(R.id.edit_income_amount)
        val spinnerSource = findViewById<Spinner>(R.id.spinner_source)
        val btnSave = findViewById<Button>(R.id.btn_save_income)

        val db = AppDatabase.getDatabase(this)
        val incomeDao = db.financeDao()

        btnSave.setOnClickListener {
            val amountText = editAmount.text.toString()
            val amount = amountText.toDoubleOrNull()
            val source = spinnerSource.selectedItem.toString()

            if (amount != null && amount > 0) {
                lifecycleScope.launch {
                    incomeDao.insertIncome(Income(source = source, amount = amount))
                    runOnUiThread {
                        Toast.makeText(this@AddIncomeActivity, "Đã lưu doanh thu", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            } else {
                Toast.makeText(this, "Vui lòng nhập số tiền hợp lệ", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
