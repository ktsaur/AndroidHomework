package ru.itis.homework2.fragments


import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.itis.homework2.R
import ru.itis.homework2.databinding.FragmentBottomSheetBinding
import ru.itis.homework2.utils.ActionType


class BottomSheetFragment : BottomSheetDialogFragment(R.layout.fragment_bottom_sheet) {
    private var binding: FragmentBottomSheetBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBottomSheetBinding.bind(view)

        initViews()
    }

    fun initViews() {
        binding?.apply {
            btn1.setOnClickListener { sendResult(action = ActionType.ADD_RANDOM)}
            btn2.setOnClickListener { sendResult(action = ActionType.REMOVE_RANDOM)}
            btn3.setOnClickListener { sendResult(action = ActionType.ADD_ONE)}
            btn4.setOnClickListener { sendResult(action = ActionType.REMOVE_ONE)}
        }
    }

    fun sendResult(action: ActionType) {
        with(binding) {
            val inputText = this?.inputText?.text.toString()
            val count = inputText.toIntOrNull()

            when (action) {
                ActionType.ADD_RANDOM, ActionType.REMOVE_RANDOM -> {
                    if (count == null || inputText.isBlank()) {
                        this?.inputText?.error = "Введите число"
                        return
                    }
                }
                ActionType.ADD_ONE, ActionType.REMOVE_ONE -> {

                }
            }

            parentFragmentManager.setFragmentResult(
                ARG_NUMBER,
                Bundle().apply {
                    putInt(ARG_NUMBER, count ?: 0)
                    putSerializable(ARG_ACTION, action)
                }
            )
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


    companion object {
        const val ARG_NUMBER = "ARG_NUMBER"
        const val ARG_ACTION = "ARG_ACTION"
    }
}