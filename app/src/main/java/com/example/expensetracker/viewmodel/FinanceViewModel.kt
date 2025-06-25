package com.example.expensetracker.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.expensetracker.data.*
import com.example.expensetracker.repository.FinanceRepository
import kotlinx.coroutines.launch

class FinanceViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FinanceRepository
    val allIncome: LiveData<List<Income>>
    val allExpenses: LiveData<List<Expense>>
    val totalIncome: LiveData<Double>
    val totalExpenses: LiveData<Double>
    val remainingBalance: MediatorLiveData<Double> = MediatorLiveData()

    init {
        val dao = AppDatabase.getDatabase(application).financeDao()
        repository = FinanceRepository(dao)
        allIncome = repository.getAllIncomes()
        allExpenses = repository.getAllExpenses()
        totalIncome = repository.getTotalIncome()
        totalExpenses = repository.getTotalExpenses()

        remainingBalance.addSource(totalIncome) { updateBalance() }
        remainingBalance.addSource(totalExpenses) { updateBalance() }
    }

    private fun updateBalance() {
        val income = totalIncome.value ?: 0.0
        val expenses = totalExpenses.value ?: 0.0
        remainingBalance.value = income - expenses
    }

    fun addIncome(income: Income) = viewModelScope.launch {
        repository.insertIncome(income)
    }

    fun addExpense(expense: Expense) = viewModelScope.launch {
        repository.insertExpense(expense)
    }
}