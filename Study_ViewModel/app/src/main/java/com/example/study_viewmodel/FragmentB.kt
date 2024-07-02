package com.example.study_viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.study_viewmodel.databinding.FragmentBBinding

class FragmentB : Fragment() {
    private lateinit var viewModel: SharedViewModel
    private var _binding: FragmentBBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        viewModel.uiState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is UiState.Loading -> {
                    // 로딩 상태 처리
                }
                is UiState.Success -> {
                    binding.editTextFromFragment2.setText(state.data)
                }
                is UiState.Failure -> {
                    // 에러 상태 처리
                }
            }
        })

        binding.sendButtonFragment2.setOnClickListener {
            val text = binding.editTextFromFragment2.text.toString()
            viewModel.selectItem(text)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
