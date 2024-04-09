package com.example.diploma.ui.repayment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RepaymentViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Возвращаемость, в %"
    }
    val text: LiveData<String> = _text
}