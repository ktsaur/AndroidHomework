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
        viewBinding.root.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                action.invoke(adapterPosition)
            }
        }
    }

    fun bindItem(itemData: AnswerModel, isSelected: Boolean) {
        with(viewBinding) {
            tvAnswerOption1.text = itemData.answer
            root.isEnabled = itemData.isClickable

            if (isSelected) {
                root.setBackgroundColor(root.context.getColor(R.color.green))
                ivIcon.setImageResource(R.drawable.ic_answer_true)
            } else {
                root.setBackgroundColor(root.context.getColor(R.color.white))
                ivIcon.setImageResource(R.drawable.ic_answer_anactive)
            }
        }
    }
}