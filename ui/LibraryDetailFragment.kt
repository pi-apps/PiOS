package com.example.memegeneratorabschlussprojektgoekhankorkmaz.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.R
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.data.Meme
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.databinding.FragmentLibraryBinding
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.databinding.FragmentLibraryDetailBinding

class LibraryDetailFragment : Fragment() {
    private lateinit var binding: FragmentLibraryDetailBinding
    private val viewModel: MemesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedMeme = viewModel.getChoosenMeme()
        if (selectedMeme != null) {
            val imgUri = selectedMeme.url.toUri().buildUpon().scheme("https").build()
            binding.imageViewLibraryDetail.load(imgUri)
        }

        binding.imageButtonBackDetail.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.imageButtonDeleteDetail.setOnClickListener {
            if (selectedMeme != null) {
                showDeleteConfirmationDialog(selectedMeme)
            }
        }

        binding.imageButtonShareDetail.setOnClickListener {
            val sharingMeme = viewModel.getChoosenMeme()
            if (sharingMeme != null) {
                shareMeme(sharingMeme.url)
            }
        }
    }

    private fun showDeleteConfirmationDialog(selectedMeme: Meme) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete Meme")
            .setMessage("Are you sure you want to delete this meme?")
            .setPositiveButton("Yes") { _, _ ->
                deleteMeme(selectedMeme)
            }
            .setNegativeButton("No") { _, _ -> }
            .show()
    }

    private fun deleteMeme(selectedMeme: Meme) {
        viewModel.deleteMeme(selectedMeme)
        findNavController().navigate(R.id.libraryFragment)
    }

    private fun shareMeme(memeUrl: String) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Check out this meme: $memeUrl")
            type = "text/plain"
        }
        val shareChooserIntent = Intent.createChooser(shareIntent, "Share this meme via...")
        startActivity(shareChooserIntent)
    }
}







