package com.example.memegeneratorabschlussprojektgoekhankorkmaz.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.adapter.MemeAdapter
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.databinding.FragmentHomeBinding

const val Tag = "HomeFragment"
class HomeFragment : Fragment() {

    private val viewModel: MemesViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObserver()
        viewModel.loadMemes()

    }

    private fun addObserver() {

        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            binding.recyclerViewHome.adapter = MemeAdapter(it.filter{it.box_count == 2}, viewModel)
        })
    }
}
