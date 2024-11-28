package ru.itis.homework2.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import ru.itis.homework2.R
import ru.itis.homework2.databinding.FragmentDetailBinding
import ru.itis.homework2.repositories.ContentRepository

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var binding: FragmentDetailBinding? = null;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)

        val title = requireArguments().getString(ARG_TITLE)
        val description = requireArguments().getString(ARG_DESCRIPTION)
        val image_url = requireArguments().getString(ARG_IMAGE_URL)

        with(binding) {
            this?.tvTitle?.text = title
            this?.tvDescription?.text = description
            //this?.ivImage?.let { Glide?.with(this@DetailFragment)?.load(image_url)?.into(it) ?:  }
            Glide.with(this@DetailFragment).load(image_url).into(this?.ivImage!!)

        }

    }

    companion object {
        private const val ARG_TITLE = "ARG_TITLE"
        private const val ARG_DESCRIPTION = "ARG_DESCRIPTION"
        private const val ARG_IMAGE_URL = "ARG_IMAGE_URL"

        fun bundle(title: String, description: String, imageUrl: String): Bundle = Bundle().apply {
            putString(ARG_TITLE, title)
            putString(ARG_DESCRIPTION, description)
            putString(ARG_IMAGE_URL, imageUrl)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}