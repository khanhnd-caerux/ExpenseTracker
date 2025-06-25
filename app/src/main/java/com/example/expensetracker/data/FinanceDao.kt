package com.example.expensetracker.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FinanceDao {
    // Income's functions
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIncome(income: Income)

    @Query("SELECT * FROM income_table")
    fun getAllIncomes(): LiveData<List<Income>>

    @Query("SELECT * FROM income_table")
    suspend fun getAllIncomesList(): List<Income>

    @Delete
    suspend fun deleteIncome(income: Income)

    // Expense's functions
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExpense(expense: Expense)

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