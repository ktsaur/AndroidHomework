package ru.itis.homework5.Fragment

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import ru.itis.homework5.R

open class BaseFragment(layoutId: Int): Fragment(layoutId) {
    protected open var composeView: ComposeView? = null

}