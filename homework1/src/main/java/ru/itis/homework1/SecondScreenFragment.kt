package ru.itis.homework1

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.itis.homework1.databinding.FragmentSecondScreenBinding

class SecondScreenFragment : Fragment(R.layout.fragment_second_screen) {

    private var binding: FragmentSecondScreenBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSecondScreenBinding.bind(view)

        val text = arguments?.getString(ARG_TEXT) ?: ""

        binding?.apply {
//            text2.setText("${text2.text} + $text")
            if (text != "") {
                text2.setText("$text")
            }
            btnSecondScreen.setOnClickListener{

                val thirdFragment = ThirdScreenFragment().apply {
                    arguments = ThirdScreenFragment.bundle(
                        text = text2.text.toString()
                    )
                }

                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, thirdFragment)
                    .addToBackStack(null)
                    .commit()

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