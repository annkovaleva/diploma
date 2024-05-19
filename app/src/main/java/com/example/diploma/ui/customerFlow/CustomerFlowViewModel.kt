package com.example.diploma.ui.customerFlow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CustomerFlowViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Возвращаемость, в %"
    }
    val text: LiveData<String> = _text
}