package com.example.memegeneratorabschlussprojektgoekhankorkmaz.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btExit.setOnClickListener {
            exitAppDialog()
        }
    }

    private fun exitAppDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Exit App")
            .setMessage("Are you sure you want to exit the app?")
            .setPositiveButton("Yes") { _, _ ->
                exitApp()
            }
            .setNegativeButton("No") { _, _ ->  }
            .show()
    }

    private fun exitApp() {
        requireActivity().finish()
    }
}
