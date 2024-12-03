package ru.itis.homework3.adapters.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.itis.homework3.R
import ru.itis.homework3.databinding.ItemAnswerOptionBinding
import ru.itis.homework3.holders.ViewHolder
import ru.itis.homework3.models.AnswerModel


class RecyclerViewAdapter(
    private var action: (Int) -> Unit,
    items: List<AnswerModel>
) : RecyclerView.Adapter<ViewHolder>() {

    private var itemsList = mutableListOf<AnswerModel>()
    var selected_position: Int = RecyclerView.NO_POSITION

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
        holder.bindItem(itemData = itemsList[position], isSelected = position == selected_position)
    }

    fun updateSelection(newPosition: Int) {
        if (newPosition == selected_position) return

        if (selected_position != RecyclerView.NO_POSITION) {
            itemsList[selected_position].isClickable = true
            itemsList[selected_position].backgroundTint = R.color.white
            itemsList[selected_position].icon = R.drawable.ic_answer_anactive
            notifyItemChanged(selected_position)
        }
        selected_position = newPosition
        itemsList[selected_position].isClickable = false
        itemsList[selected_position].backgroundTint = R.color.green
        itemsList[selected_position].icon = R.drawable.ic_answer_true
        notifyItemChanged(selected_position)

    }
}