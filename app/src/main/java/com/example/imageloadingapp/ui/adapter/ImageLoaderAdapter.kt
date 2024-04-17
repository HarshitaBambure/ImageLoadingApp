package com.example.imageloadingapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imageloadingapp.data.model.response.ImageListResponse
import com.example.imageloadingapp.databinding.ImageItemBinding
import com.example.imageloadingapp.utils.imageloadinghelper.LoadImage

class ImageLoaderAdapter : RecyclerView.Adapter<ImageLoaderAdapter.ImageViewHolder>() {
    private val listImages = mutableListOf<ImageListResponse.PhotoListResponseItem>()

    open class ImageViewHolder(val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: ImageListResponse.PhotoListResponseItem) {
            LoadImage.loadImage(binding.img.context, item.urls.regular, binding.img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ImageItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listImages.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindData(listImages.get(position))
    }

    fun updateData(listData:List<ImageListResponse.PhotoListResponseItem>){
        listImages.addAll(listData)
        notifyDataSetChanged()
    }
}