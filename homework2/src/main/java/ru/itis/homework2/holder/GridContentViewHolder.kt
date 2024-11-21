package ru.itis.homework2.holder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.RequestManager
import ru.itis.homework2.databinding.ItemGridBinding
import ru.itis.homework2.databinding.ItemListBinding
import ru.itis.homework2.models.ListPictureItemModel

class GridContentViewHolder(
    private val viewBinding: ItemGridBinding,
    private val requestManager: RequestManager,
    private val action: (Int) -> Unit
): ViewHolder(viewBinding.root) {

    init {
        viewBinding.root.setOnClickListener{
            action.invoke(adapterPosition)
        }
    }

    fun bindItem(itemData: ListPictureItemModel) {
        viewBinding.tvDesc.text = itemData.title

        requestManager.load(itemData.imageUrl)
            .into(viewBinding.ivPicture)
    }
}