package com.example.expensetracker.repository

import androidx.lifecycle.LiveData
import com.example.expensetracker.data.*

class FinanceRepository(private val dao: FinanceDao) {
    fun getAllIncomes(): LiveData<List<Income>> = dao.getAllIncomes()
    fun getAllExpenses(): LiveData<List<Expense>> = dao.getAllExpenses()
    fun getTotalIncome(): LiveData<Double> = dao.getTotalIncome()
    fun getTotalExpenses(): LiveData<Double> = dao.getTotalExpenses()

    suspend fun insertIncome(income: Income) = dao.insertIncome(income)
    suspend fun insertExpense(expense: Expense) = dao.insertExpense(expense)
}