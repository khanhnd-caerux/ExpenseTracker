package com.example.expensetracker.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.R
import com.example.expensetracker.data.Expense
import java.text.SimpleDateFormat
import java.util.*
import java.text.NumberFormat

class ExpenseAdapter(
    private val expenses: MutableList<Expense>,
    private val onDelete: (Expense) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Không cần ánh xạ gì thêm nếu bạn để text cố định trong layout
    }

    class ExpenseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTimestamp: TextView = view.findViewById(R.id.txt_timestamp)
        val txtCategory: TextView = view.findViewById(R.id.txt_category)
        val txtAmount: TextView = view.findViewById(R.id.txt_amount)
        val btnDelete: Button = view.findViewById(R.id.btn_delete)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_expense_header, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_expense, parent, false)
            ExpenseViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ExpenseViewHolder) {
            if (position == 0 || expenses.isEmpty()) return  // tránh crash

            val expense = expenses[position - 1]

            val date = Date(expense.date)
            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
            holder.txtTimestamp.text = formattedDate
            holder.txtCategory.text = expense.category

            val formattedAmount = NumberFormat.getInstance(Locale("vi", "VN")).format(expense.amount)
            holder.txtAmount.text = "$formattedAmount"

            holder.btnDelete.setOnClickListener {
                onDelete(expense)
            }
        }
    }

    override fun getItemCount(): Int = expenses.size + 1  // +1 for header

    fun updateData(newData: List<Expense>) {
        expenses.clear()
        expenses.addAll(newData)
        notifyDataSetChanged()
    }
}