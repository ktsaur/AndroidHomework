package ru.itis.homework3

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class SampleRVAdapter(
    manager: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(manager, lifecycle) {


    override fun getItemCount(): Int = 6

    override fun createFragment(position: Int): Fragment {
        return QuestionnaireFragment.getInstance(position)
    }

}