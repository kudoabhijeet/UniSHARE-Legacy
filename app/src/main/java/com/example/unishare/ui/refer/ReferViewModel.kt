package com.example.unishare.ui.refer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReferViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is refer to a friend Fragment"
    }
    val text: LiveData<String> = _text
    
}