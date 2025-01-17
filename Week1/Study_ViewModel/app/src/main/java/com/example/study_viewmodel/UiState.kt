package com.example.study_viewmodel

sealed class UiState<out T>{
    data object Loading: UiState<Nothing>()
    data class Success<out T>(val data: T): UiState<T>()
    data class Failure(val code: Int?, val message: String): UiState<Nothing>()
}