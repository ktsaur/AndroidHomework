package ru.itis.homework2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import androidx.recyclerview.widget.RecyclerView
import ru.itis.homework2.databinding.ItemListBinding
import ru.itis.homework2.holder.ListContentViewHolder
import ru.itis.homework2.models.ListPictureItemModel
import java.lang.Math.random
import kotlin.random.Random

class ListContentAdapter(
    private val contextManager: RequestManager,
    private val action: (Int) -> Unit,
    items: List<ListPictureItemModel>
): RecyclerView.Adapter<ListContentViewHolder>() {

    private val dataList = mutableListOf<ListPictureItemModel>()

    init{
        dataList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListContentViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListContentViewHolder(
            viewBinding = binding,
            requestManager = contextManager,
            action = action
        )
    }

    override fun onBindViewHolder(holder: ListContentViewHolder, position: Int) {
        holder.bindItem(itemData =  dataList[position],
            count = dataList.size)
    }

    override fun getItemCount(): Int = dataList.size

    fun updateItems(newItems: List<ListPictureItemModel>) {
        dataList.clear()
        dataList.addAll(newItems)
        notifyDataSetChanged()
    }

    fun getDataList(): List<ListPictureItemModel> {
        return dataList
    }
    //ТУТ МЫ ДОЛЖНЫ ПРОПИСАТЬ МЕТОДЫ ДЛЯ УДАЛЕНИЯ, ДОБАВЛЕНИЯ И ТД

    fun removeAll() {
        dataList.clear()
        notifyDataSetChanged()
    }


}