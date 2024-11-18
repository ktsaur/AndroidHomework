package ru.itis.homework2.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import ru.itis.homework2.R
import ru.itis.homework2.adapter.ListContentAdapter
import ru.itis.homework2.databinding.FragmentMainBinding
import ru.itis.homework2.models.ListPictureItemModel
import ru.itis.homework2.repositories.ContentRepository
import ru.itis.homework2.repositories.Dataset
import ru.itis.homework2.utils.ActionType
import kotlin.random.Random


class MainFragment : Fragment(R.layout.fragment_main) {

    private var binding: FragmentMainBinding? = null

    private var rvAdapter: ListContentAdapter? = null

    private val currentDataList = mutableListOf<ListPictureItemModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        val glide = Glide.with(requireContext())

        initRecyclerView(requestManager = glide)

        with(binding) {
            this!!.floatingActionButton.show()
            this.floatingActionButton.setOnClickListener{
                BottomSheetFragment().show(parentFragmentManager, "BottomSheetFragment")
            }


            parentFragmentManager.setFragmentResultListener(
                BottomSheetFragment.ARG_NUMBER,
                this@MainFragment
            ) { _, bundle ->
                val text = bundle.getInt(BottomSheetFragment.ARG_NUMBER)
                val type_action = bundle.getSerializable(BottomSheetFragment.ARG_ACTION) as? ActionType

                when(type_action) {
                    ActionType.ADD_RANDOM -> {
                        val newItems = getListItems(text)
                        newItems.forEach { newItem ->
                            val randomIndex = Random.nextInt(currentDataList.size + 1)
                            currentDataList.add(randomIndex, newItem)
                        }
                        rvAdapter?.updateItems(currentDataList)
                    }
                    ActionType.REMOVE_RANDOM -> {
                        val removeCount = minOf(text, currentDataList.size)
                        repeat(removeCount) {
                            currentDataList.removeAt(Random.nextInt(currentDataList.size))
                        }
                        rvAdapter?.updateItems(currentDataList)
                    }
                    ActionType.ADD_ONE -> {
                        val newItem = Dataset.getRandomElement()
                        val randomIndex = Random.nextInt(currentDataList.size + 1)
                        currentDataList.add(randomIndex, newItem)
                        rvAdapter?.updateItems(currentDataList)
                    }
                    ActionType.REMOVE_ONE -> {
                        currentDataList.removeAt(Random.nextInt(currentDataList.size))
                        rvAdapter?.updateItems(currentDataList)
                    }
                    null -> {
                        Log.d("Ничего не произошло какая-то фигня", "Ничего не произошло какая-то фигня")
                    }
                }

            }
        }
    }

    private fun initRecyclerView(requestManager: RequestManager) {
        if (currentDataList.isEmpty()) {
            currentDataList.addAll(ContentRepository().getListContent(context = requireContext()))
        }

        rvAdapter = ListContentAdapter(
            contextManager = requestManager,
            action1 = { position ->
                handleButtonClick(position)
            },
            action2 = { position ->
                onItemClick(position)
            },
            items = currentDataList
        )



        binding?.recyclerView?.apply {
            val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            this.layoutManager = layoutManager
            adapter = rvAdapter
        }
    }



    private fun onItemClick(position: Int) {
        if (position == 0) return
        val dataList = rvAdapter?.getDataList()

        val detailFragment = DetailFragment().apply {
            arguments = dataList?.get(position)?.let {
                DetailFragment.bundle(
                    title = it.title,
                    description = dataList[position - 1].description,
                    imageUrl = dataList[position - 1].imageUrl
                )
            }
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.container, detailFragment)
            .addToBackStack(null) //чтобы можно было вернуться назад
            .commit()
    }

    private fun handleButtonClick(position: Int) {
        when (position) {
            0 -> binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            1 -> binding?.recyclerView?.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (position == 0) 2
                        else 1
                    }
                }
            }
        }

    }



    fun getListItems(count: Int): List<ListPictureItemModel>{
        val listItems = mutableListOf<ListPictureItemModel>()
        for (i in 1..count) {
            val elem = Dataset.getRandomElement()
            listItems.add(elem)
        }
        return listItems
    }


}