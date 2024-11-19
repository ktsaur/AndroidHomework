package ru.itis.homework2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import androidx.recyclerview.widget.RecyclerView
import ru.itis.homework2.R
import ru.itis.homework2.databinding.ItemButtonBinding
import ru.itis.homework2.databinding.ItemGridBinding
import ru.itis.homework2.databinding.ItemHolderFirstTypeBinding
import ru.itis.homework2.databinding.ItemHolderSecondTypeBinding
import ru.itis.homework2.databinding.ItemListBinding
import ru.itis.homework2.holder.ButtonViewHolder
import ru.itis.homework2.holder.FirstTypeViewHolder
import ru.itis.homework2.holder.GridContentViewHolder
import ru.itis.homework2.holder.ListContentViewHolder
import ru.itis.homework2.holder.SecondTypeViewHolder
import ru.itis.homework2.models.ListPictureItemModel
import ru.itis.homework2.utils.DisplayType


class ListContentAdapter(
    private val contextManager: RequestManager,
    private val action1: (Int) -> Unit,
    private var action2: (Int) -> Unit,
    items: List<ListPictureItemModel>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataList = mutableListOf<ListPictureItemModel>()
    private var currentDisplayType: DisplayType = DisplayType.LIST

    init{
        dataList.addAll(items)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_button -> {
                val viewBinding = ItemButtonBinding.inflate( LayoutInflater.from(parent.context), parent, false)
                ButtonViewHolder(viewBinding = viewBinding, action = action1)

            }
            R.layout.item_list -> {
                val viewBinding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ListContentViewHolder(
                    viewBinding = viewBinding,
                    requestManager = contextManager,
                    action = action2
                )
            }

            R.layout.item_grid -> {
                val viewBinding = ItemGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                GridContentViewHolder (
                    viewBinding = viewBinding,
                    requestManager = contextManager,
                    action = action2
                )
            }

            R.layout.item_holder_first_type -> {
                val viewBinding = ItemHolderFirstTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                FirstTypeViewHolder(
                    viewBinding = viewBinding,
                    requestManager = contextManager,
                    action = action2
                )
            }

            R.layout.item_holder_second_type -> {
                val viewBinding = ItemHolderSecondTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                SecondTypeViewHolder(
                    viewBinding = viewBinding,
                    requestManager = contextManager,
                    action = action2
                )
            }

            else -> throw IllegalStateException("Unknown holder")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ButtonViewHolder) {
            holder.bindItem()
        } else if (holder is ListContentViewHolder) {
            holder.bindItem(itemData = dataList[position - 1])
        } else if (holder is GridContentViewHolder) {
            holder.bindItem(itemData = dataList[position - 1])
        } else if (holder is FirstTypeViewHolder) {
            holder.bindItem(itemData = dataList[position - 1])
        }
        else if (holder is SecondTypeViewHolder) {
            holder.bindItem(itemData = dataList[position - 1])
        }
    }

    override fun getItemCount(): Int = dataList.size + 1

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return R.layout.item_button

        return when(currentDisplayType) {
            DisplayType.LIST -> R.layout.item_list
            DisplayType.GRID -> R.layout.item_grid
            DisplayType.VERTICAL_GRID -> {
                val indexPosition = (position - 1) % 4
                if (indexPosition == 0 || indexPosition == 3) {
                    R.layout.item_holder_first_type
                } else {
                    R.layout.item_holder_second_type
                }
            }
        }
    }

    fun updateItems(newItems: List<ListPictureItemModel>) {
        dataList.clear()
        dataList.addAll(newItems)
        notifyDataSetChanged()
    }

    fun getDataList(): List<ListPictureItemModel> {
        return dataList
    }

    fun updateDisplayType(newDisplayType: DisplayType) {
        currentDisplayType = newDisplayType
        notifyDataSetChanged()
    }
    //ТУТ МЫ ДОЛЖНЫ ПРОПИСАТЬ МЕТОДЫ ДЛЯ УДАЛЕНИЯ, ДОБАВЛЕНИЯ И ТД
}