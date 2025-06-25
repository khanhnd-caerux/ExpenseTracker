package com.example.expensetracker.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.R
import com.example.expensetracker.data.Income
import java.text.SimpleDateFormat
import java.util.*
import java.text.NumberFormat

class IncomeAdapter(
    private val incomes: MutableList<Income>,
    private val onDelete: (Income) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Không cần ánh xạ gì thêm nếu bạn để text cố định trong layout
    }

    class IncomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTimestamp: TextView = view.findViewById(R.id.txt_timestamp)
        val txtSource: TextView = view.findViewById(R.id.txt_source)
        val txtAmount: TextView = view.findViewById(R.id.txt_amount)
        val btnDelete: Button = view.findViewById(R.id.btn_delete)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_income_header, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_income, parent, false)
            IncomeViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is IncomeViewHolder) {
            if (position == 0 || incomes.isEmpty()) return  // tránh crash

            val income = incomes[position - 1]

            val date = Date(income.date)
            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
            holder.txtTimestamp.text = formattedDate
            holder.txtSource.text = income.source

            val formattedAmount = NumberFormat.getInstance(Locale("vi", "VN")).format(income.amount)
            holder.txtAmount.text = "$formattedAmount"

            holder.btnDelete.setOnClickListener {
                onDelete(income)
            }
        }
    }

    override fun getItemCount(): Int = incomes.size + 1  // +1 for header

    fun updateData(newData: List<Income>) {
        incomes.clear()
        incomes.addAll(newData)
        notifyDataSetChanged()
    }
}