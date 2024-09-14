package com.example.idiotchefassistant.resultBlock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import coil.load
import com.example.idiotchefassistant.databinding.FragmentImageDetailBinding

class ImageDetailFragment : DialogFragment() {
    private var _binding: FragmentImageDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageUrl = arguments?.getString("IMAGE_URL")

        imageUrl?.let {
            binding.fullImageView.load(it)
        }
        binding.fullImageView.setOnClickListener {
            dismiss() // 關閉 Fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(imageUrl: String): ImageDetailFragment {
            val fragment = ImageDetailFragment()
            val args = Bundle()
            args.putString("IMAGE_URL", imageUrl)
            fragment.arguments = args
            return fragment
        }
    }
}