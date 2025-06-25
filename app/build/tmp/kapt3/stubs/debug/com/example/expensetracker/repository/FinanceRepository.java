package com.example.expensetracker.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0012\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006J\u0012\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00070\u0006J\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0006J\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u0006J\u0019\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J\u0019\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0015"}, d2 = {"Lcom/example/expensetracker/repository/FinanceRepository;", "", "dao", "Lcom/example/expensetracker/data/FinanceDao;", "(Lcom/example/expensetracker/data/FinanceDao;)V", "getAllExpenses", "Landroidx/lifecycle/LiveData;", "", "Lcom/example/expensetracker/data/Expense;", "getAllIncomes", "Lcom/example/expensetracker/data/Income;", "getTotalExpenses", "", "getTotalIncome", "insertExpense", "", "expense", "(Lcom/example/expensetracker/data/Expense;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertIncome", "income", "(Lcom/example/expensetracker/data/Income;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class FinanceRepository {
    @org.jetbrains.annotations.NotNull
    private final com.example.expensetracker.data.FinanceDao dao = null;
    
    public FinanceRepository(@org.jetbrains.annotations.NotNull
    com.example.expensetracker.data.FinanceDao dao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.util.List<com.example.expensetracker.data.Income>> getAllIncomes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.util.List<com.example.expensetracker.data.Expense>> getAllExpenses() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.Double> getTotalIncome() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.Double> getTotalExpenses() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object insertIncome(@org.jetbrains.annotations.NotNull
    com.example.expensetracker.data.Income income, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object insertExpense(@org.jetbrains.annotations.NotNull
    com.example.expensetracker.data.Expense expense, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}