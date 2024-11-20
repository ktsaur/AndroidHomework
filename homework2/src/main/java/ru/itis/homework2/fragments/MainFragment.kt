package ru.itis.homework2.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
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
import ru.itis.homework2.utils.DisplayType
import ru.itis.homework2.utils.SwipeHelper
import kotlin.random.Random


class MainFragment : Fragment(R.layout.fragment_main) {

    private var binding: FragmentMainBinding? = null

    private var rvAdapter: ListContentAdapter? = null

    private val currentDataList = mutableListOf<ListPictureItemModel>()

    private var currentDisplayType = DisplayType.LIST

    companion object {
        private const val KEY_DISPLAY_TYPE = "KEY_DISPLAY_TYPE"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        val glide = Glide.with(requireContext())

        currentDisplayType = savedInstanceState?.getSerializable(KEY_DISPLAY_TYPE) as? DisplayType
            ?: DisplayType.LIST

        initRecyclerView(requestManager = glide)
        actionByBottomSheet()


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY_DISPLAY_TYPE, currentDisplayType)
    }

    private fun actionByBottomSheet() {
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
                        Log.d("Ничего не произошло", "Ничего не произошло")
                    }
                }

            }
        }
    }

    private fun initRecyclerView(requestManager: RequestManager) {
        if (currentDataList.isEmpty()) {
            currentDataList.addAll(ContentRepository().getListContent())
        }

        rvAdapter = ListContentAdapter(
            contextManager = requestManager,
            action1 = { position ->
                onButtonClick(position)
            },
            action2 = { position ->
                onItemClick(position)
            },
            items = currentDataList
        )

        binding?.recyclerView?.apply {
            layoutManager = when (currentDisplayType) {
                DisplayType.LIST -> LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                DisplayType.GRID -> GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
                DisplayType.VERTICAL_GRID -> GridLayoutManager(context, 2, RecyclerView.VERTICAL, false).apply {
                    spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            return if (position == 0 || (position - 1) % 4 in listOf(0, 3)) 2 else 1
                        }
                    }
                }
            }
            adapter = rvAdapter

        }
        if (currentDisplayType == DisplayType.LIST) { swipeToDelete() }
    }



    private fun onItemClick(position: Int) {
        if (position == 0) return
        val dataList = rvAdapter?.getDataList()

        val detailFragment = DetailFragment().apply {
            arguments = dataList?.get(position)?.let {
                DetailFragment.bundle(
                    title = dataList[position - 1].title,
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

    private fun onButtonClick(position: Int) {
        when (position) {
            0 -> {
                currentDisplayType = DisplayType.LIST
                binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                swipeToDelete()
            }
            1 -> {
                currentDisplayType = DisplayType.GRID
                binding?.recyclerView?.layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false).apply {
                    spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            return if (position == 0) 3
                            else 1
                        }
                    }
                }
            }
            2 -> {
                currentDisplayType = DisplayType.VERTICAL_GRID
                binding?.recyclerView?.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false).apply {
                    spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            return if (position == 0 || (position - 1) % 4 in listOf(0, 3)) 2 else 1
                        }
                    }
                }
            }
        }
        rvAdapter?.updateDisplayType(currentDisplayType)

    }


    private fun swipeToDelete() {
        val swipeHelper = object: SwipeHelper() {
            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                if (currentDisplayType == DisplayType.LIST) {
                    return makeMovementFlags(0, ItemTouchHelper.LEFT)
                } else {
                    return makeMovementFlags(0, 0)
                }
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (currentDisplayType == DisplayType.LIST) {
                    val position = viewHolder.adapterPosition
                    currentDataList.removeAt(position - 1)
                    rvAdapter?.updateItems(currentDataList)
                }
            }
        }
        val itemTouchhelper = ItemTouchHelper(swipeHelper)
        itemTouchhelper.attachToRecyclerView(binding!!.recyclerView)
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