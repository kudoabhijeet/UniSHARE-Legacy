package com.example.unishare.ui.refer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.unishare.R


class ReferFragment : Fragment() {


    private lateinit var referViewModel: ReferViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        referViewModel =
            ViewModelProvider(this).get(ReferViewModel::class.java)
        val root = inflater.inflate(R.layout.refer_fragment, container, false)
        val textView: TextView = root.findViewById(R.id.text_refer)
        referViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

}