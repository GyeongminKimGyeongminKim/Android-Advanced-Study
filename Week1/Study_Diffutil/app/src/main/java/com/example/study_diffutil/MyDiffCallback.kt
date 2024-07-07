package com.example.study_diffutil

import androidx.recyclerview.widget.DiffUtil

class MyDiffCallback : DiffUtil.ItemCallback<Monster>() {

    override fun areItemsTheSame(oldItem: Monster, newItem: Monster): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Monster, newItem: Monster): Boolean {
        return oldItem == newItem
    }
}