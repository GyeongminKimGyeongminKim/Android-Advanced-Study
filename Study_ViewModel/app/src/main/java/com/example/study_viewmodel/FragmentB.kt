package com.example.study_viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.study_viewmodel.databinding.FragmentBBinding
import com.google.android.material.textfield.TextInputEditText

class FragmentB : Fragment() {
    private lateinit var viewModel: SharedViewModel
    private lateinit var editText: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_b, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        editText = view.findViewById(R.id.edit_text_from_fragment_2)

        viewModel.uiState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is UiState.Loading -> {
                    // 로딩 상태 처리
                }
                is UiState.Success -> {
                    editText.setText(state.data)

                }
                is UiState.Failure -> {
                    // 에러 상태 처리
                }
            }
        })

        view.findViewById<View>(R.id.send_button_fragment_2).setOnClickListener {
            val text = editText.text.toString()
            viewModel.selectItem(text)
        }

        return view
    }
}