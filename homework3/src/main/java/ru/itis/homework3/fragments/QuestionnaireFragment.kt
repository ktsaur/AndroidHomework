package ru.itis.homework3.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import ru.itis.homework3.R
import ru.itis.homework3.adapters.recyclerView.RecyclerViewAdapter
import ru.itis.homework3.databinding.FragmentQuestionnaireBinding
import ru.itis.homework3.models.AnswerModel

class QuestionnaireFragment: Fragment(R.layout.fragment_questionnaire) {

    private lateinit var viewPager: ViewPager2 //fn + shift + f6

    private var rvAdapter: RecyclerViewAdapter? = null

    private var color: Int = R.color.white
    private var icon: Int =  R.drawable.ic_answer_anactive

    private val binding: FragmentQuestionnaireBinding by viewBinding(
        FragmentQuestionnaireBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewPager = requireActivity().findViewById(R.id.view_pager)

    }

    fun initViews() {
        arguments?.getInt(POSITION_KEY)?.let { position ->
            val question: String
            val answers: List<AnswerModel>

            when(position) {
                0 -> {
                    question = "Как объявить неизменяемую переменную в Kotlin?"
                    answers = listOf(
                        AnswerModel(color, "var x = 10", icon),
                        AnswerModel(color, "let x = 10", icon),
                        AnswerModel(color,  "const x = 10", icon),
                        AnswerModel(color,  "val x = 10", icon),
                        AnswerModel(color, "final x = 10", icon))
                        binding.btnBack.isEnabled = false
                }
                1 -> {
                    question = "Какой тип возвращает функция, если ничего не возвращает?"
                    answers = listOf(AnswerModel(color, "Int", icon),
                        AnswerModel(color, "Unit", icon),
                        AnswerModel(color,  "void", icon),
                        AnswerModel(color,  "null", icon),
                        AnswerModel(color, "Nothing", icon))
                }
                2 -> {
                    question = "Как вызвать метод fun greet() {} объекта person?"
                    answers = listOf(AnswerModel(color, "greet(person)", icon),
                        AnswerModel(color, "greet()", icon),
                        AnswerModel(color,  "person.greet()", icon),
                        AnswerModel(color,  "call person.greet()",icon),
                        AnswerModel(color, "this.greet()" , icon))
                }
                3 -> {
                    question = "Как задать значение по умолчанию для параметра функции?"
                    answers = listOf(AnswerModel(color, "fun greet(name: String?) {}", icon),
                        AnswerModel(color, "fun greet(name: String = \"Guest\") {}", icon),
                        AnswerModel(color,   "fun greet(name: String { \"Guest\" }) {}", icon),
                        AnswerModel(color,   "fun greet(name: \"Guest\") {}", icon),
                        AnswerModel(color,  "fun greet(name: String?) = \"Guest\"", icon))
                }
                4 -> {
                    question = "Что делает оператор ?.?"
                    answers = listOf(AnswerModel(color,  "Проверяет, не равен ли объект null.", icon),
                        AnswerModel(color, "Выполняет действие, только если объект не null.", icon),
                        AnswerModel(color,  "Генерирует исключение, если объект равен null.",icon),
                        AnswerModel(color,   "Приводит объект к nullable типу.", icon),
                        AnswerModel(color, "Создаёт новый экземпляр объекта.", icon))
                }
                else -> {
                    question = "Какой вид коллекции является неизменяемым?"
                    answers = listOf (
                        AnswerModel(color,   "ArrayList",  icon),
                        AnswerModel(color, "LinkedList", icon),
                        AnswerModel(color,  "List", icon),
                        AnswerModel(color,  "HashSet", icon),
                        AnswerModel(color, "MutableList", icon))
                        binding.btnForward.text = "Завершить"
                }
            }

            rvAdapter = RecyclerViewAdapter(
                action = {position ->
                    onItemClick(position)
                },
                items = answers
            )
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = rvAdapter
            }

            with(binding) {
                tvQuestion.text = question
                tvCounter.text = "${position + 1}/6"

                btnForward.setOnClickListener{
                    if (position < 5) {
                        viewPager.setCurrentItem(position + 1, true)
                    } else if (position == 5) {
                        btnForward.setOnClickListener {
                            Snackbar.make(requireView(), "Ответы сохранены", Snackbar.LENGTH_LONG).show()
                        }
                    }
                }

                btnBack.setOnClickListener{
                    if (position > 0) {
                        viewPager.setCurrentItem(position - 1, true)
                    }
                }
            }

        }
    }

    fun onItemClick(position: Int) {
        //в адаптере нужно написать метод чтобы менять картинку и цвет юэкграунда и вызывать его в этом методе. посмотреть гит по андроиду
        rvAdapter?.changeItemColor(
            position = position,
            color = R.color.grin,
            icon = R.drawable.ic_answer_true)

    }

    companion object{
        const val POSITION_KEY = "POSITION"

        fun getInstance(position: Int) = QuestionnaireFragment().apply {
            arguments = bundleOf(POSITION_KEY to position)
        }

    }

}