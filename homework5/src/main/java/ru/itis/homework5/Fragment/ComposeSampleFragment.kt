package ru.itis.homework5.Fragment

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.itis.homework5.R
import ru.itis.homework5.databinding.FragmentComposeSampleBinding
import ru.itis.homework5.ui.CancelButtonExample
import ru.itis.homework5.ui.LaunchButtonExample
import ru.itis.homework5.ui.MainScreen2
import ru.itis.homework5.ui.RadioButtonLogicalSelection
import ru.itis.homework5.ui.RadioButtonSingleSelection
import ru.itis.homework5.ui.SimpleOutlinedTextFieldSample

class ComposeSampleFragment: BaseFragment(R.layout.fragment_compose_sample) {

    private val viewBinding: FragmentComposeSampleBinding by viewBinding(FragmentComposeSampleBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.composeContainerId.setContent {
            /* скаффолд это заранее заготовленная вьюшка,  у которой есть тулбар и основной контент */
            Scaffold { padding ->
                Column {
                    SimpleOutlinedTextFieldSample()
                    RadioButtonSingleSelection()
                    RadioButtonLogicalSelection()
                    MainScreen2()
                    Row (
                        Modifier.padding(start = 25.dp,end = 25.dp, top = 50.dp)
                    ) {
                        LaunchButtonExample()
                        CancelButtonExample()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        composeView = null
    }
}