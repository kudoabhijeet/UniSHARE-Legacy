package com.example.unishare.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.unishare.R
import com.example.unishare.ui.home.HomeViewModel

class GalleryViewModel : ViewModel() {
    private lateinit var galleryViewModel: GalleryViewModel


    private val _text = MutableLiveData<String>().apply {
        value = "This is update Fragment"
    }
    val text: LiveData<String> = _text
}