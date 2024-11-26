package ru.itis.homework3.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.itis.homework3.R
import ru.itis.homework3.adapters.cardView.SampleRVAdapter
import ru.itis.homework3.databinding.FragmentViewPagerBinding

class ViewPagerFragment: Fragment(R.layout.fragment_view_pager) {

    private val binding: FragmentViewPagerBinding by viewBinding(FragmentViewPagerBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SampleRVAdapter (
            manager = parentFragmentManager,
            lifecycle = this.lifecycle,
        )
        binding.viewPager.adapter = adapter
    }
}