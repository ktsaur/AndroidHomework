package ru.itis.homework3.adapters.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.itis.homework3.databinding.ItemAnswerOptionBinding
import ru.itis.homework3.holders.ViewHolder
import ru.itis.homework3.models.AnswerModel


class RecyclerViewAdapter(
    private var action: (Int) -> Unit,
    items: List<AnswerModel>
) : RecyclerView.Adapter<ViewHolder>() {

    private var itemsList = mutableListOf<AnswerModel>()

    init {
        itemsList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAnswerOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding = binding, action = action)
    }

    override fun getItemCount() = itemsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(itemData = itemsList[position], itemCount)
    }


    fun changeItemColor(position: Int, color: Int, icon: Int) {
        itemsList[position].backgroundTint = color
        itemsList[position].icon = icon
        notifyItemChanged(position)
    }
}