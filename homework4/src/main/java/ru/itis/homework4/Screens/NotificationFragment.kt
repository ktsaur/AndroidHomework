package ru.itis.homework4.Screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.itis.homework4.Activity.MainActivity
import ru.itis.homework4.Notification.NotificationData
import ru.itis.homework4.Notification.NotificationHandler
import ru.itis.homework4.Notification.NotificationType
import ru.itis.homework4.R
import ru.itis.homework4.databinding.FragmentNotificationBinding

class NotificationFragment: Fragment(R.layout.fragment_notification) {
    private var binding: FragmentNotificationBinding? = null
    private var notificationHandler: NotificationHandler? = null
    private var selectedImportance: NotificationType = NotificationType.DEFAULT
    private var colorsFlag = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(notificationHandler == null) {
            notificationHandler = (requireActivity() as? MainActivity)?.notificationHandler
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotificationBinding.bind(view)

        initActionWithThemes()
        initActionWithImage()
        initImportanceSpinner()
        actionWithNotification(selectedImportance)
    }

    fun initImportanceSpinner() {
        val spinner: Spinner = binding?.spinnerImpotrances?.findViewById(R.id.spinner_impotrances) ?: return

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.importance_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                selectedImportance = when (selectedItem) {
                    "MAX" -> NotificationType.MAX
                    "LOW" -> NotificationType.LOW
                    "HIGHT" -> NotificationType.HIGHT
                    else -> NotificationType.DEFAULT
                }
                Log.d("Spinner", "Selected Importance: $selectedImportance")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedImportance = NotificationType.DEFAULT
            }
        }
    }

    private var counter = 0
    fun actionWithNotification (importance: NotificationType) {
        with(binding) {
            val button = this?.btnShowNotification
            val header = this?.header?.text.toString()
            val message = this?.message?.text.toString()

            button?.setOnClickListener{
                if(header.isBlank()) {
//                    Snackbar.make(requireView(), "Заголовок уведомления пустой", Snackbar.LENGTH_LONG).show()
                    Toast.makeText(context, "Заголовок уведомления пустой", Toast.LENGTH_SHORT).show()
                } else {
                    notificationHandler?.showNotification(
                        data = NotificationData(
                            id = counter++,
                            title = header,
                            message = message,
                            notificationType = selectedImportance
                        )
                    )
                }
            }
        }
    }

    fun initActionWithImage() {
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
    }

    fun initActionWithThemes() {
        with(binding){
            this?.dropdownMenu?.imageButton?.setOnClickListener{
                if (colorsFlag) {
                    dropdownMenu.linear.visibility = View.VISIBLE
                    colorsFlag = false
                } else{
                    dropdownMenu.linear.visibility = View.GONE
                    colorsFlag = true
                }
            }

            this?.dropdownMenu?.linear?.findViewById<ImageButton>(R.id.image_sand)?.setOnClickListener{
                changeTheme("sand")
            }
            this?.dropdownMenu?.linear?.findViewById<ImageButton>(R.id.image_blue)?.setOnClickListener{
                changeTheme("blue")
            }
            this?.dropdownMenu?.linear?.findViewById<ImageButton>(R.id.image_green)?.setOnClickListener{
                changeTheme("green")
            }
            this?.btnResetColor?.setOnClickListener {
                changeTheme("base")
            }


        }
    }

    fun changeTheme(themeName: String) {
        val intent = Intent(requireContext(), MainActivity::class.java).apply {
            putExtra("selected_theme", themeName)
        }
        startActivity(intent)
        requireActivity().finish()

        val newColor = when (themeName) {
            "sand" -> requireContext().getColor(R.color.sand)
            "blue" -> requireContext().getColor(R.color.blue)
            "green" -> requireContext().getColor(R.color.green)
            else -> requireContext().getColor(R.color.primary)
        }
        binding?.includeImage?.icCancel?.findViewById<ImageView>(R.id.ic_cancel)?.setColorFilter(newColor)
    }


    override fun onDestroy() {
        super.onDestroy()
        notificationHandler = null
    }
}