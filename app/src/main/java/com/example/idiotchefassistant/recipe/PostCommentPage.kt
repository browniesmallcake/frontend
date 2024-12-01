package com.example.idiotchefassistant.recipe

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.idiotchefassistant.databinding.FragmentPostCommentBinding

class PostCommentPage : DialogFragment() {
    private var _binding: FragmentPostCommentBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostCommentBinding.inflate(inflater, container, false)
        val view = binding.root
        val rating = binding.reviewStar
        val comment = binding.commentBlock
        val close = binding.closeButton
        val upload = binding.uploadButton


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

