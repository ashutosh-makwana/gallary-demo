package com.`fun`.gallerydroid.presentation.ui.album

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.`fun`.gallerydroid.common.Constants
import com.`fun`.gallerydroid.common.load
import com.`fun`.gallerydroid.databinding.ItemPhotoListBinding

class AlbumPhotoAdapter :
    ListAdapter<String, AlbumPhotoAdapter.AlbumListViewHolder>(AlbumPhotoComparator) {
    override fun onBindViewHolder(holder: AlbumListViewHolder, position: Int) {
        val imageUrl = getItem(position)
        imageUrl?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumListViewHolder {
        val binding =
            ItemPhotoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumListViewHolder(binding)
    }

    inner class AlbumListViewHolder(private val binding: ItemPhotoListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photoUrl: String) {
            Log.e(Constants.TAG, "before loading $photoUrl")
            binding.ivAlbumPhoto.load(photoUrl)
        }
    }

    companion object {
        private val AlbumPhotoComparator = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem
        }
    }
}