package com.example.expensetracker.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FinanceDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIncome(income: Income)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExpense(expense: Expense)

    @Query("SELECT * FROM income_table")
    fun getAllIncomes(): LiveData<List<Income>>

    // Expense's functions
    @Query("SELECT * FROM expense_table")
    fun getAllExpenses(): LiveData<List<Expense>>

    @Query("SELECT * FROM expense_table")
    suspend fun getAllExpensesList(): List<Expense>


    @Delete
    suspend fun deleteExpense(expense: Expense)

    @Query("SELECT SUM(amount) FROM income_table")
    fun getTotalIncome(): LiveData<Double>

    @Query("SELECT SUM(amount) FROM expense_table")
    fun getTotalExpenses(): LiveData<Double>
}