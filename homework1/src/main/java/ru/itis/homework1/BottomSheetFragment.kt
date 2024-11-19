package ru.itis.homework1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.itis.homework1.databinding.FragmentBottomSheetBinding


class BottomSheetFragment : BottomSheetDialogFragment(R.layout.fragment_bottom_sheet){
    private var binding: FragmentBottomSheetBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBottomSheetBinding.bind(view)

        initViews()
    }

    fun initViews() {
        binding?.apply {

            btnSubmit.isEnabled = false

            inputText.addTextChangedListener {
                val editText = inputText.text.toString()
                btnSubmit.isEnabled = editText.isNotBlank()
            }

            btnSubmit.setOnClickListener {
                val inputText = inputText.text.toString()
                parentFragmentManager.setFragmentResult(
                    REQUEST_KEY,
                    Bundle().apply { putString(KEY_TEXT, inputText) }
                )
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val REQUEST_KEY = "REQUEST_TEXT"
        const val KEY_TEXT = "KEY_TEXT"
    }
}