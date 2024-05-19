package com.example.diploma.ui.additional

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.diploma.databinding.FragmentAdditionalBinding

class AdditionalFragment : Fragment() {

    private var _binding: FragmentAdditionalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val additionalViewModel =
            ViewModelProvider(this)[AdditionalViewModel::class.java]

        _binding = FragmentAdditionalBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}