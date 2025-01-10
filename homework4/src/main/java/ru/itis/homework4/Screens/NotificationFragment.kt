package ru.itis.homework4.Screens

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import ru.itis.homework4.R
import ru.itis.homework4.databinding.FragmentNotificationBinding

class NotificationFragment: Fragment(R.layout.fragment_notification) {
    private var binding: FragmentNotificationBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotificationBinding.bind(view)

        initViews()
    }

    fun initViews() {
        with(binding) {
            val imageView = this?.includeImage?.imageCircle?.findViewById<ImageView>(R.id.iv_image)
            val cancelIcon = this?.includeImage?.imageCircle?.findViewById<ImageView>(R.id.ic_cancel)

            imageView?.setOnClickListener{
                imageView.setImageResource(R.drawable.image)
                cancelIcon?.visibility = View.VISIBLE
            }

            cancelIcon?.setOnClickListener{
                imageView?.setImageDrawable(null)
                imageView?.setBackgroundResource(R.color.gray)
                cancelIcon.visibility = View.INVISIBLE
            }
        }

//        val colors_array = listOf(R.color.sand, R.color.blue, R.color.green)
//        val spinner: Spinner = binding.colorsSpinner.findViewById(R.id.colors_spinner)
//// Create an ArrayAdapter using the string array and a default spinner layout.
//        ArrayAdapter.createFromResource(
//            this@NotificationFragment,
//            R.color.sand,
//            android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            // Specify the layout to use when the list of choices appears.
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            // Apply the adapter to the spinner.
//            spinner.adapter = adapter
//        }
    }
}