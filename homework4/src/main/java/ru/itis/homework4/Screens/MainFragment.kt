package ru.itis.homework4.Screens

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import ru.itis.homework4.Activity.MainActivity
import ru.itis.homework4.Notification.NotificationData
import ru.itis.homework4.Notification.NotificationHandler
import ru.itis.homework4.Notification.NotificationType
import ru.itis.homework4.R
import ru.itis.homework4.databinding.FragmentMainBinding

class MainFragment: Fragment(R.layout.fragment_main) {
    private var binding: FragmentMainBinding? = null
    private var notificationHandler: NotificationHandler? = null
    private var selectedImportance: NotificationType = NotificationType.DEFAULT
    private var colorsFlag = true

    private var selectedImageUri: Uri? = null

    private val getImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            val imageView = binding?.includeImage?.imageCircle?.findViewById<ImageView>(R.id.iv_image)
            imageView?.setImageURI(selectedImageUri)
            val cancelIcon = binding?.includeImage?.imageCircle?.findViewById<ImageView>(R.id.ic_cancel)
            cancelIcon?.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(notificationHandler == null) {
            notificationHandler = (requireActivity() as? MainActivity)?.notificationHandler
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

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
                    context?.getString(R.string.Max) -> NotificationType.MAX
                    context?.getString(R.string.Low)-> NotificationType.LOW
                    context?.getString(R.string.Hight) -> NotificationType.HIGHT
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

            button?.setOnClickListener{
                val header = this?.header?.text.toString()
                val message = this?.message?.text.toString()

                if(header.isBlank()) {
                    Toast.makeText(context, context?.getString(R.string.Empty_title_notification), Toast.LENGTH_SHORT).show()
                }
                else if (message.isBlank()) {
                    Toast.makeText(context, context?.getString(R.string.Empty_message_notification), Toast.LENGTH_SHORT).show()
                } else{
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
//                if (imageView.drawable != null) Toast.makeText(context, context?.getString(R.string.Image_uploaded), Toast.LENGTH_SHORT).show()
//                imageView.setImageResource(R.drawable.image)
//                imageView.setBackgroundResource(R.drawable.circle_shape)
//                cancelIcon?.visibility = View.VISIBLE
                            openGallery()
            }

            cancelIcon?.setOnClickListener{
                imageView?.setImageDrawable(null)
                imageView?.setBackgroundResource(R.drawable.circle_shape)
                cancelIcon.visibility = View.INVISIBLE
            }
        }
    }

    private fun openGallery() {
        getImage.launch(context?.getString(R.string.MIME_TYPE_IMAGE))
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
                context?.getString(R.string.sand)?.let { it1 -> changeTheme(it1) }
            }
            this?.dropdownMenu?.linear?.findViewById<ImageButton>(R.id.image_blue)?.setOnClickListener{
                context?.getString(R.string.blue)?.let { it1 -> changeTheme(it1) }
            }
            this?.dropdownMenu?.linear?.findViewById<ImageButton>(R.id.image_green)?.setOnClickListener{
                context?.getString(R.string.green)?.let { it1 -> changeTheme(it1) }
            }
            this?.btnResetColor?.setOnClickListener {
                context?.getString(R.string.base)?.let { it1 -> changeTheme(it1) }
            }
        }
    }

    fun changeTheme(themeName: String) {
        val newTheme = when (themeName) {
            context?.getString(R.string.sand) -> R.style.SandTheme_AndroidHomework
            context?.getString(R.string.blue) -> R.style.BlueTheme_AndroidHomework
            context?.getString(R.string.green) -> R.style.GreenTheme_AndroidHomework
            else -> R.style.Base_Theme_AndroidHomework
        }
        requireActivity().intent.putExtra(context?.getString(R.string.current_theme), newTheme)
        requireActivity().recreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        notificationHandler = null
    }
}