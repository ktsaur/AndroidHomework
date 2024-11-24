package ru.itis.homework3

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.itis.homework3.databinding.FragmentQuestionnaireBinding

class QuestionnaireFragment: Fragment(R.layout.fragment_questionnaire) {

    private lateinit var viewPager: ViewPager2

    private val binding: FragmentQuestionnaireBinding by viewBinding(
        FragmentQuestionnaireBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = requireActivity().findViewById(R.id.view_pager)

        initViews()
    }

    fun initViews() {
        arguments?.getInt(POSITION_KEY)?.let { position ->
            val question: String
            val answer_option1: String
            val answer_option2: String
            val answer_option3: String
            val answer_option4: String
            val answer_option5: String

            when(position) {
                0 -> {
                    question = "Как объявить неизменяемую переменную в Kotlin?"
                    answer_option1 = "var x = 10"
                    answer_option2 = "let x = 10"
                    answer_option3 = "const x = 10"
                    answer_option4 = "val x = 10"
                    answer_option5 = "final x = 10"

                    binding.btnBack.isEnabled = false
                }
                1 -> {
                    question = "Какой тип возвращает функция, если ничего не возвращает?"
                    answer_option1 = "Int"
                    answer_option2 = "Unit"
                    answer_option3 = "void"
                    answer_option4 = "null"
                    answer_option5 = "Nothing"
                }
                2 -> {
                    question = "Как вызвать метод fun greet() {} объекта person?"
                    answer_option1 = "greet(person)"
                    answer_option2 = "greet()"
                    answer_option3 = "person.greet()"
                    answer_option4 = "call person.greet()"
                    answer_option5 = "this.greet()"
                }
                3 -> {
                    question = "Как задать значение по умолчанию для параметра функции?"
                    answer_option1 = "fun greet(name: String?) {}"
                    answer_option2 = "fun greet(name: String = \"Guest\") {}"
                    answer_option3 = "fun greet(name: String { \"Guest\" }) {}"
                    answer_option4 = "fun greet(name: \"Guest\") {}"
                    answer_option5 = "fun greet(name: String?) = \"Guest\""
                }
                4 -> {
                    question = "Что делает оператор ?.?"
                    answer_option1 = "Проверяет, не равен ли объект null."
                    answer_option2 = "Выполняет действие, только если объект не null."
                    answer_option3 = "Генерирует исключение, если объект равен null."
                    answer_option4 = "Приводит объект к nullable типу."
                    answer_option5 = "Создаёт новый экземпляр объекта."
                }
                else -> {
                    question = "Какой вид коллекции является неизменяемым?"
                    answer_option1 = "ArrayList"
                    answer_option2 = "LinkedList"
                    answer_option3 = "List"
                    answer_option4 = "HashSet"
                    answer_option5 = "MutableList"

                    binding.btnForward.isEnabled = false
                }
            }

            with(binding) {
                tvCounter.text = "${position + 1}/6"
                tvQuestion.text = question
                tvAnswerOption1.text = answer_option1
                tvAnswerOption2.text = answer_option2
                tvAnswerOption3.text = answer_option3
                tvAnswerOption4.text = answer_option4
                tvAnswerOption5.text = answer_option5

                btnForward.setOnClickListener{
                    if (position < 5) {
                        viewPager.setCurrentItem(position + 1, true)
                    }
                }

                btnBack.setOnClickListener{
                    if (position > 0) {
                        viewPager.setCurrentItem(position - 1, true)
                    }
                }

                cvAnswerOption1.setOnClickListener{
                    ivFirst.setImageResource(R.drawable.ic_answer_true)
                }

                cvAnswerOption2.setOnClickListener{
                    ivSecond.setImageResource(R.drawable.ic_answer_true)
                }

                cvAnswerOption3.setOnClickListener{
                    ivThird.setImageResource(R.drawable.ic_answer_true)
                }

                cvAnswerOption4.setOnClickListener{
                    ivFourth.setImageResource(R.drawable.ic_answer_true)
                }

                cvAnswerOption5.setOnClickListener{
                    ivFifth.setImageResource(R.drawable.ic_answer_true)
                }

            }

        }
    }

    companion object{
        const val POSITION_KEY = "POSITION"

        fun getInstance(position: Int) = QuestionnaireFragment().apply {
            arguments = bundleOf(POSITION_KEY to position)
        }

    }

}