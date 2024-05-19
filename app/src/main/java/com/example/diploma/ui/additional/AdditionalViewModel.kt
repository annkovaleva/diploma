package com.example.diploma.ui.additional

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AdditionalViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Поток клиентов за год"
    }
    val text: LiveData<String> = _text
}