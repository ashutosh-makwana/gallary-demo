package com.`fun`.gallerydroid.presentation.ui.album

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.`fun`.gallerydroid.common.Constants
import com.`fun`.gallerydroid.data.remote.dto.AlbumDto
import com.`fun`.gallerydroid.databinding.ItemAlbumListBinding

class UserAlbumAdapter(private val onAlbumClicked: (Int, Int) -> Unit) :
    ListAdapter<AlbumDto, UserAlbumAdapter.PostListViewHolder>(PostComparator) {

    private lateinit var albumPhotoAdapter: AlbumPhotoAdapter

    override fun onBindViewHolder(holder: PostListViewHolder, position: Int) {
        val album = getItem(position)
        album?.let { holder.bind(it) }
        holder.itemView.setOnClickListener {
            onAlbumClicked.invoke(album.id, position)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostListViewHolder {
        val binding =
            ItemAlbumListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostListViewHolder(binding)
    }

    inner class PostListViewHolder(private val binding: ItemAlbumListBinding) :
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
        }
    }

    companion object {
        private val PostComparator = object : DiffUtil.ItemCallback<AlbumDto>() {
            override fun areItemsTheSame(oldItem: AlbumDto, newItem: AlbumDto): Boolean {
                return oldItem.photos == newItem.photos
            }

            override fun areContentsTheSame(oldItem: AlbumDto, newItem: AlbumDto): Boolean =
                oldItem == newItem
        }
    }
}