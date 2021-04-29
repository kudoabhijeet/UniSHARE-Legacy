package com.example.unishare.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.unishare.R
import com.example.unishare.ui.home.HomeViewModel


class GalleryFragment : Fragment() {
    private lateinit var galleryViewModel: GalleryViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_update_profile, container, false)

        galleryViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        print("Hello ")
    }

}