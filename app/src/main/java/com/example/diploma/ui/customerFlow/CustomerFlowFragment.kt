package com.example.diploma.ui.customerFlow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.diploma.databinding.FragmentCustomerFlowBinding

class CustomerFlowFragment : Fragment() {

    private var _binding: FragmentCustomerFlowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val customerFlowViewModel =
            ViewModelProvider(this)[CustomerFlowViewModel::class.java]

        _binding = FragmentCustomerFlowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}