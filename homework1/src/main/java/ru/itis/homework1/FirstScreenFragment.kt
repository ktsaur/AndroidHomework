package ru.itis.homework1

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.itis.homework1.databinding.FragmentFirstScreenBinding

class FirstScreenFragment : Fragment(R.layout.fragment_first_screen) {

    private var binding: FragmentFirstScreenBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFirstScreenBinding.bind(view)

        initViews()

    }

    fun initViews() {

        binding?.apply {

            btn1.setOnClickListener {
                //мы сначала создаем фрагмент
                val secondFragment = SecondScreenFragment().apply {
                    arguments = SecondScreenFragment.bundle(
                        text = text1.text.toString()
                    )
                }

                //потом нужно заменить текущий фрагмент на второй, то есть мы добавляем его в стек
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, secondFragment)
                    .addToBackStack(null) //чтобы можно было вернуться назад
                    .commit()
            }


            btn2.setOnClickListener{

                //сначала третий фрагмент надо создать
                val thirdFragment = ThirdScreenFragment().apply {
                    arguments = ThirdScreenFragment.bundle(
                        text = text1.text.toString()
                    )
                }

                val secondFragment = SecondScreenFragment().apply {
                    arguments = SecondScreenFragment.bundle(
                        text = text1.text.toString()
                    )
                }

                /*
                Так как при свайпе назад на третьем фрагменте нам нужно вернуться сначала на второй, потом на третий, нам надо
                сначала добавить в стек второй фрагмент, только потом третий
                 */

                //сначала добавили второй фрагмент
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, secondFragment)
                    .addToBackStack(null)
                    .commit()

                //заменяем текущий на третий. ParentFragmentManager - возвращает ТЕКУЩИЙ фрагмент
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, thirdFragment)
                    .addToBackStack(null)
                    .commit()
            }

            btn3.setOnClickListener {
                BottomSheetFragment().show(parentFragmentManager, "TextInputBottomSheetFragment")
            }


            parentFragmentManager.setFragmentResultListener(
                BottomSheetFragment.REQUEST_KEY,
                this@FirstScreenFragment
            ) { _, bundle ->
                val text = bundle.getString(BottomSheetFragment.KEY_TEXT)
                binding?.text1?.setText(text)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}