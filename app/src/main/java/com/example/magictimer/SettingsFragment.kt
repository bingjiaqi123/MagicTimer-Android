package com.example.magictimer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.magictimer.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnContext.setOnClickListener {
            findNavController().navigate(R.id.action_SettingsFragment_to_ContextFragment)
        }

        binding.btnSettingsReturn.setOnClickListener {
            findNavController().navigate(R.id.action_SettingsFragment_to_FirstFragment)
        }
    }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}