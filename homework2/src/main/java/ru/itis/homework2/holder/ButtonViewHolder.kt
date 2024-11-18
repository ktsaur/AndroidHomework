package ru.itis.homework2.holder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.RequestManager
import ru.itis.homework2.databinding.ItemButtonBinding

class ButtonViewHolder(
    private val viewBinding: ItemButtonBinding,
    private val action: (Int) -> Unit
): ViewHolder(viewBinding.root) {

    fun bindItem() {
        viewBinding.btnList.setOnClickListener { action(0) }
        viewBinding.btnNet.setOnClickListener { action(1) }
    }

}