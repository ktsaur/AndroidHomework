package ru.itis.homework2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import androidx.recyclerview.widget.RecyclerView
import ru.itis.homework2.R
import ru.itis.homework2.databinding.ItemButtonBinding
import ru.itis.homework2.databinding.ItemListBinding
import ru.itis.homework2.holder.ButtonViewHolder
import ru.itis.homework2.holder.ListContentViewHolder
import ru.itis.homework2.models.ListPictureItemModel


class ListContentAdapter(
    private val contextManager: RequestManager,
    private val action1: (Int) -> Unit,
    private var action2: (Int) -> Unit,
    items: List<ListPictureItemModel>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataList = mutableListOf<ListPictureItemModel>()

    init{
        dataList.addAll(items)
        notifyDataSetChanged()
    }
    companion object {
        const val VIEW_TYPE_BUTTONS = 0
        const val VIEW_TYPE_CONTENT = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_BUTTONS -> {
                val viewBinding = ItemButtonBinding.inflate( LayoutInflater.from(parent.context), parent, false)
                ButtonViewHolder(viewBinding = viewBinding, action = action1)

            }
            VIEW_TYPE_CONTENT -> {
                val viewBinding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ListContentViewHolder(
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
            holder.bindItem(itemData = dataList[position - 1], count = dataList.size)
        }
    }

    override fun getItemCount(): Int = dataList.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            VIEW_TYPE_BUTTONS
        } else {
            VIEW_TYPE_CONTENT
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
    //ТУТ МЫ ДОЛЖНЫ ПРОПИСАТЬ МЕТОДЫ ДЛЯ УДАЛЕНИЯ, ДОБАВЛЕНИЯ И ТД
}