package com.`fun`.gallerydroid.presentation.ui.album

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.`fun`.gallerydroid.common.Constants
import com.`fun`.gallerydroid.data.remote.dto.AlbumDto
import com.`fun`.gallerydroid.databinding.ItemAlbumListBinding

class UserAlbumAdapter(private val onAlbumClicked: (Int, Int) -> Unit) :
    ListAdapter<AlbumDto, UserAlbumAdapter.UserAlbumViewHolder>(UserAlbumComparator) {

    private lateinit var albumPhotoAdapter: AlbumPhotoAdapter

    override fun onBindViewHolder(holder: UserAlbumViewHolder, position: Int) {
        val album = getItem(position)
        album?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserAlbumViewHolder {
        val binding =
            ItemAlbumListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserAlbumViewHolder(binding)
    }

    inner class UserAlbumViewHolder(private val binding: ItemAlbumListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: AlbumDto) {
            binding.album = album
            Log.e(Constants.TAG, "photos bind ${album.photos}")
            binding.rvAlbumPhoto.isVisible = !album.photos.isNullOrEmpty()
            binding.rvAlbumPhoto.apply {
                setHasFixedSize(true)
                albumPhotoAdapter = AlbumPhotoAdapter()
                adapter = albumPhotoAdapter
                albumPhotoAdapter.submitList(album.photos)
            }
            binding.cardAlbum.setOnClickListener {
                if (!binding.rvAlbumPhoto.isVisible)
                    onAlbumClicked.invoke(album.id, adapterPosition)
                else
                    binding.rvAlbumPhoto.visibility = View.GONE
            }
        }
    }

    companion object {
        private val UserAlbumComparator = object : DiffUtil.ItemCallback<AlbumDto>() {
            override fun areItemsTheSame(oldItem: AlbumDto, newItem: AlbumDto): Boolean {
                return oldItem.photos == newItem.photos
            }

            override fun areContentsTheSame(oldItem: AlbumDto, newItem: AlbumDto): Boolean =
                oldItem == newItem
        }
    }
}