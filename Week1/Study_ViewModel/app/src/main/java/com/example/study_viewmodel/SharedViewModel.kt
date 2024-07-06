package com.example.study_viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _uiState = MutableLiveData<UiState<String>>()
    val uiState: LiveData<UiState<String>> = _uiState

    fun selectItem(item: String) {
        _uiState.value = UiState.Success(item)
    }

    fun setLoading() {
        _uiState.value = UiState.Loading
    }

    fun setError(code: Int?, message: String) {
        _uiState.value = UiState.Failure(code, message)
    }
}