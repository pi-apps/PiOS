package com.example.memegeneratorabschlussprojektgoekhankorkmaz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.R
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.data.Meme
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.databinding.MemeItemBinding
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.ui.MemesViewModel

class MemeAdapter(
    private val dataSet: List<Meme>, private val memeViewModel: MemesViewModel

) : RecyclerView.Adapter<MemeAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: MemeItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            MemeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataSet[position]
        val imgUri = item.url.toUri().buildUpon().scheme("https").build()
        holder.binding.mcMemeItem.setOnClickListener {
            memeViewModel.addChoosenMeme(item)
            val navController = holder.itemView.findNavController()
            navController.navigate(R.id.editFragment)
        }

        holder.binding.ivMemeItem.load(imgUri) {
            error(R.drawable.qo5mfyde5v_350)
            transformations(RoundedCornersTransformation(10f))
        }

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}