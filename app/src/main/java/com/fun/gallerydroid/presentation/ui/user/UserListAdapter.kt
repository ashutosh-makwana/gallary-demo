package com.`fun`.gallerydroid.presentation.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.`fun`.gallerydroid.databinding.ItemUserListBinding
import com.`fun`.gallerydroid.domain.model.User

class UserListAdapter(private val onUserClicked: (Int) -> Unit) :
    ListAdapter<User, UserListAdapter.UserListViewHolder>(UserComparator) {
    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val user = getItem(position)
        user?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserListViewHolder {
        val binding =
            ItemUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserListViewHolder(binding)
    }

    inner class UserListViewHolder(private val binding: ItemUserListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.user = user
            binding.cardUser.setOnClickListener {
                onUserClicked.invoke(user.id)
            }
        }
    }

    companion object {
        private val UserComparator = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem
        }
    }
}