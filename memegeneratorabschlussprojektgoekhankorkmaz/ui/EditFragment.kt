package com.example.memegeneratorabschlussprojektgoekhankorkmaz.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.BuildConfig
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.R
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.data.Meme
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.databinding.FragmentEditBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

const val TAG = "EditFragment"

class EditFragment : Fragment() {
    private lateinit var binding: FragmentEditBinding
    private val viewModel: MemesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageButtonBackEdit.setOnClickListener {
            findNavController().navigateUp()
        }
        addObserver()
        binding.buttonCreateMeme.setOnClickListener {
            val topText = binding.editTextTopText.text.toString()
            val bottomText = binding.editTextBottomText.text.toString()

            launchCoroutine {
                try {
                    val response = viewModel.sendPostToRepo(
                        templateId = viewModel.currentMeme.value?.id ?: "",
                        username = "UserFree",
                        password = "grisu2017",
                        topText = topText,
                        bottomText = bottomText
                    )

                    if (response.success) {
                        response.data?.let { editedMeme ->
                            val memeId = editedMeme.page_url
                            val meme = Meme(id = memeId, url = editedMeme.url)
                            viewModel.insertMemeToLibrary(meme)
                            viewModel.addChoosenMeme(meme)
                            findNavController().navigate(R.id.homeFragment)
                            showToast("meme successfully created and saved in your Library", 6000)
                        }
                    } else {

                        Log.e(TAG, "Fehler beim Erstellen des Memes: ${response.error_message}")
                        showToast("error while creatiing, pls rebuild", 6000)
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Fehler bei der Netzwerkanfrage: ${e.message}")
                }
            }
        }
    }
    private fun launchCoroutine(block: suspend () -> Unit) {
        lifecycleScope.launch {
            block()
        }
    }
    private fun showToast(message: String, duration: Int) {
        val toast = Toast.makeText(requireContext(), message, duration)
        toast.show()
    }

    fun addObserver() {
        viewModel.currentMeme.observe(viewLifecycleOwner, Observer {
            val imgUri = it.url.toUri().buildUpon().scheme("https").build()
            binding.imageViewMemeEdit.load(imgUri)
        })
    }
}




