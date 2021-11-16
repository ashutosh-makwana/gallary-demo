package com.`fun`.gallerydroid.presentation.ui.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.`fun`.gallerydroid.data.remote.dto.AlbumDto
import com.`fun`.gallerydroid.databinding.ItemAlbumListBinding

class UserAlbumAdapter(private val onUserClicked: (Int)-> Unit) :
    ListAdapter<AlbumDto, UserAlbumAdapter.PostListViewHolder>(PostComparator) {
    override fun onBindViewHolder(holder: PostListViewHolder, position: Int) {
        val album = getItem(position)
        album?.let { holder.bind(it) }
        holder.itemView.setOnClickListener {
            onUserClicked.invoke(album.id)
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
        }

    }

    companion object {
        private val PostComparator = object : DiffUtil.ItemCallback<AlbumDto>() {
            override fun areItemsTheSame(oldItem: AlbumDto, newItem: AlbumDto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: AlbumDto, newItem: AlbumDto): Boolean =
                oldItem == newItem
        }
    }
}