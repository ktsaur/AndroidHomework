package ru.itis.homework5.Fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.itis.homework5.R
import ru.itis.homework5.databinding.FragmentComposeSampleBinding
import ru.itis.homework5.ui.CancelButtonExample
import ru.itis.homework5.ui.LaunchButtonExample
import ru.itis.homework5.ui.RadioButtonLogicalSelection
import ru.itis.homework5.ui.RadioButtonSingleSelection
import ru.itis.homework5.ui.SimpleOutlinedTextFieldSample
import ru.itis.homework5.ui.ThreadPool

class ComposeSampleFragment: BaseFragment(R.layout.fragment_compose_sample) {
    private var job: Job? = null
    private val scope: LifecycleCoroutineScope = lifecycleScope //от этого scope надо вызывать все корутины и от него останавливать

    private var count_of_corutines by mutableStateOf("")  //количество введенных корутин
    private var how_launch by mutableStateOf("Sequentially") // последовательно или параллельно
    private var selectedLogic by mutableStateOf("Cancel") //логика запуска: отменить при сворачивании или нет
    private var selectedDispatcher by mutableStateOf("Default")

    private val viewBinding: FragmentComposeSampleBinding by viewBinding(FragmentComposeSampleBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setView()

    }

    fun setView() {
        viewBinding.composeContainerId.setContent {
            /* скаффолд это заранее заготовленная вьюшка,  у которой есть тулбар и основной контент */
            Scaffold { padding ->
                Column {
                    SimpleOutlinedTextFieldSample(
                        selectedValue = count_of_corutines,
                        onValueChange = { newValue ->
                            count_of_corutines = newValue
                            Log.d("ComposeFragment", "Количество корутин: $newValue")
                        }
                    )
                    RadioButtonSingleSelection(
                        selectedValue = how_launch,
                        onValueChange = { newValue ->
                            how_launch = newValue
                            Log.d("ComposeFragment", "Как будет запускаться: $newValue")
                        }
                    )
                    RadioButtonLogicalSelection(
                        selectedValue = selectedLogic,
                        onValueChange = { newValue ->
                            selectedLogic = newValue
                            Log.d("ComposeFragment", "Выбранная логика: $newValue")
                        }
                    )
                    ThreadPool(
                        selectedValue = selectedDispatcher,
                        onValueChange = { newValue ->
                            selectedDispatcher = newValue
                            Log.d("ComposeFragment", "Выбранный диспетчер: $newValue")
                        }
                    )
                    Row (
                        Modifier.padding(start = 25.dp,end = 25.dp, top = 50.dp)
                    ) {
                        LaunchButtonExample({ launch() })
                        CancelButtonExample({ cancel() })
                    }
                }
            }
        }
    }

    fun launch() { //запуск корутин

    }

    fun cancel() { //отмена корутин

    }

    //короче нужно запускать все корутины через job и соответсвенно вызывать launch{}
    override fun onStart() {
        super.onStart()
        scope.launch {
            delay(3000L)
        }
    }

    override fun onStop() {
        super.onStop()
        scope.cancel() // отмена всех корутин
        job = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        composeView = null
        job = null
    }
}