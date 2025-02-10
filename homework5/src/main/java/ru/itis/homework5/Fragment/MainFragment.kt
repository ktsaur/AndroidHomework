package ru.itis.homework5.Fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
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

class MainFragment: BaseFragment(R.layout.fragment_compose_sample) {
    private var fragmentJob = SupervisorJob()
    private val scope: CoroutineScope = lifecycleScope //от этого scope надо вызывать все корутины и от него останавливать
    val jobsList = mutableListOf<Job>()
    private var isError by mutableStateOf(false)

    private var count_of_corutines by mutableStateOf("")  //количество введенных корутин
    private var how_launch by mutableStateOf(context?.getString(R.string.sequentially)) // последовательно или параллельно
    private var selectedLogic by mutableStateOf(context?.getString(R.string.cancel)) //отменить при сворачивании или нет
    private var selectedDispatcher by mutableStateOf(context?.getString(R.string.Default))

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
                            isError = false
                            Log.d("ComposeFragment", "Количество корутин: $newValue")
                        },
                        isError = isError,
                        setError = { newError -> isError = newError }
                    )
                    RadioButtonSingleSelection(
                        selectedValue = how_launch.toString(),
                        onValueChange = { newValue ->
                            how_launch = newValue
                            Log.d("ComposeFragment", "Как будет запускаться: $newValue")
                        }
                    )
                    RadioButtonLogicalSelection(
                        selectedValue = selectedLogic.toString(),
                        onValueChange = { newValue ->
                            selectedLogic = newValue
                            Log.d("ComposeFragment", "Выбранная логика: $newValue")
                        }
                    )
                    ThreadPool(
                        selectedValue = selectedDispatcher.toString(),
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
        if (count_of_corutines.isEmpty()) {
            isError = true
            return
        }
        isError = false

        val dispatchers = when (selectedDispatcher) {
            context?.getString(R.string.Default) -> Dispatchers.Default
            context?.getString(R.string.IO) -> Dispatchers.IO
            context?.getString(R.string.main) -> Dispatchers.Main
            else -> Dispatchers.Unconfined
        }
        val coroutineScope = if (selectedLogic == context?.getString(R.string.Continue)) GlobalScope else scope

        when {
            how_launch == context?.getString(R.string.parallel) -> {
                repeat(count_of_corutines.toInt()) { count ->
                    val job = coroutineScope.launch(dispatchers) {
                        try {
                            delay(1000L)
                            Log.i("Coroutin is start", "Корутина $count запущена")
                        } catch (e: Exception) {
                            Toast.makeText(context,
                                context?.getString(R.string.error_starting_coroutine), Toast.LENGTH_SHORT).show()
                        }
                    }
                    jobsList.add(job)
                }
            }
            else -> {
                coroutineScope.launch(dispatchers){
                    try {
                        repeat(count_of_corutines.toInt()) { count ->
                            val job = scope.async {
                                delay(1000L)
                                Log.i("Coroutine started", "Корутина $count запущена")
                            }
                            jobsList.add(job)
                            job.await()
                        }
                    }catch (e: Exception) {
                        Toast.makeText(context,
                            context?.getString(R.string.error_starting_coroutine), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun cancel() {
        if (jobsList.isEmpty()) {
            Toast.makeText(context,
                context?.getString(R.string.coroutines_were_not_running), Toast.LENGTH_SHORT).show()
            return
        }
        Toast.makeText(context,
            context?.getString(R.string.canceled_coroutines_count, jobsList.size), Toast.LENGTH_SHORT).show()
        Log.i("Количество корутин, которое было отменено", "${jobsList.size}")
        jobsList.forEach { it.cancel() }
        jobsList.clear()
    }


    override fun onStop() {
        super.onStop()
        if (selectedLogic == context?.getString(R.string.cancel)) {
            cancel()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        composeView = null
        fragmentJob.cancel()
    }
}