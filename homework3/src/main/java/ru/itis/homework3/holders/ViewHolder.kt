package ru.itis.homework3.holders

import androidx.recyclerview.widget.RecyclerView
import ru.itis.homework3.R
import ru.itis.homework3.databinding.ItemAnswerOptionBinding
import ru.itis.homework3.models.AnswerModel

class ViewHolder(
    private val viewBinding: ItemAnswerOptionBinding,
    private val action: (Int) -> Unit
) : RecyclerView.ViewHolder(viewBinding.root){

    init {
        viewBinding.root.setOnClickListener{
            action.invoke(adapterPosition)
        }
    }

    fun bindItem(itemData: AnswerModel, itemCount: Int) {
        with(viewBinding) {
            tvAnswerOption1.text = itemData.answer
            root.setBackgroundColor(root.context.getColor(itemData.backgroundTint))
            ivIcon.setImageResource(itemData.icon)
        }
    }
}