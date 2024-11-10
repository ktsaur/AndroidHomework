package ru.itis.homework1

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.itis.homework1.databinding.FragmentThirdScreenBinding

class ThirdScreenFragment : Fragment(R.layout.fragment_third_screen) {
    private var binding: FragmentThirdScreenBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentThirdScreenBinding.bind(view)

        val text = arguments?.getString(ARG_TEXT) ?: ""

        binding?.apply {
            if (text != "") {
                text3.setText("$text")
            }
        }

    }

    companion object {
        private const val ARG_TEXT = "ARG_TEXT"
        fun bundle(text: String): Bundle = Bundle().apply {
            putString(ARG_TEXT, text)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}