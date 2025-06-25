package com.example.expensetracker.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\fH\'J\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\rH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ\u0014\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\r0\fH\'J\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\rH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ\u000e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\fH\'J\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\fH\'J\u0019\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\u0016\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\n\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0017"}, d2 = {"Lcom/example/expensetracker/data/FinanceDao;", "", "deleteExpense", "", "expense", "Lcom/example/expensetracker/data/Expense;", "(Lcom/example/expensetracker/data/Expense;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteIncome", "income", "Lcom/example/expensetracker/data/Income;", "(Lcom/example/expensetracker/data/Income;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllExpenses", "Landroidx/lifecycle/LiveData;", "", "getAllExpensesList", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllIncomes", "getAllIncomesList", "getTotalExpenses", "", "getTotalIncome", "insertExpense", "insertIncome", "app_debug"})
@androidx.room.Dao
public abstract interface FinanceDao {
    
    @androidx.room.Insert(onConflict = 5)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertIncome(@org.jetbrains.annotations.NotNull
    com.example.expensetracker.data.Income income, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM income_table")
    @org.jetbrains.annotations.NotNull
    public abstract androidx.lifecycle.LiveData<java.util.List<com.example.expensetracker.data.Income>> getAllIncomes();
    
    @androidx.room.Query(value = "SELECT * FROM income_table")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getAllIncomesList(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.example.expensetracker.data.Income>> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteIncome(@org.jetbrains.annotations.NotNull
    com.example.expensetracker.data.Income income, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 5)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertExpense(@org.jetbrains.annotations.NotNull
    com.example.expensetracker.data.Expense expense, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM expense_table")
    @org.jetbrains.annotations.NotNull
    public abstract androidx.lifecycle.LiveData<java.util.List<com.example.expensetracker.data.Expense>> getAllExpenses();
    
    @androidx.room.Query(value = "SELECT * FROM expense_table")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getAllExpensesList(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.example.expensetracker.data.Expense>> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteExpense(@org.jetbrains.annotations.NotNull
    com.example.expensetracker.data.Expense expense, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT SUM(amount) FROM income_table")
    @org.jetbrains.annotations.NotNull
    public abstract androidx.lifecycle.LiveData<java.lang.Double> getTotalIncome();
    
    @androidx.room.Query(value = "SELECT SUM(amount) FROM expense_table")
    @org.jetbrains.annotations.NotNull
    public abstract androidx.lifecycle.LiveData<java.lang.Double> getTotalExpenses();
}