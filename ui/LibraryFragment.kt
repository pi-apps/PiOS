package com.example.memegeneratorabschlussprojektgoekhankorkmaz.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.adapter.LibraryAdapter
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.databinding.FragmentLibraryBinding

class LibraryFragment : Fragment() {
    private val viewModel: MemesViewModel by activityViewModels()
    private lateinit var binding: FragmentLibraryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObserver()

    }
    fun addObserver() {
        viewModel.libraryList.observe(viewLifecycleOwner, Observer {
            binding.recyclerViewLibrary.adapter = LibraryAdapter(it, viewModel)
        })
    }



}