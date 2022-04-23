package com.mtcn.androidroomkotlin.ui.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.mtcn.androidroomkotlin.R
import com.mtcn.androidroomkotlin.databinding.FragmentHomeBinding
import com.mtcn.androidroomkotlin.databinding.FragmentNotesBinding
import com.mtcn.androidroomkotlin.ui.DrawOnBitmapActivity
import com.mtcn.androidroomkotlin.ui.NewWordActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        init()
        return root
    }

    private fun init() {
        binding.btnDraw.setOnClickListener {
            val intent = Intent(context, DrawOnBitmapActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}