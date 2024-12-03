package ru.itis.homework2.holder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.RequestManager
import ru.itis.homework2.databinding.ItemHolderFirstTypeBinding
import ru.itis.homework2.databinding.ItemListBinding
import ru.itis.homework2.models.ListPictureItemModel

class FirstTypeViewHolder(
    private val viewBinding: ItemHolderFirstTypeBinding,
    private val requestManager: RequestManager,
    private val action: (Int) -> Unit
): ViewHolder(viewBinding.root)  {

    init {
    viewBinding.root.setOnClickListener{
        action.invoke(adapterPosition)
        }
    }

    fun bindItem(itemData: ListPictureItemModel) {
        viewBinding.headerTv.text = itemData.title

        requestManager.load(itemData.imageUrl)
            .into(viewBinding.pictureIv)
    }
}

