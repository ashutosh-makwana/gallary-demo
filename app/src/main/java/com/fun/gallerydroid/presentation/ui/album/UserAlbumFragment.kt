package com.`fun`.gallerydroid.presentation.ui.album

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.`fun`.gallerydroid.common.Constants
import com.`fun`.gallerydroid.data.remote.dto.AlbumDto
import com.`fun`.gallerydroid.data.remote.dto.PhotoDto
import com.`fun`.gallerydroid.databinding.FragmentItemListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserAlbumFragment : Fragment() {

    private val albumViewModel by viewModels<UserAlbumViewModel>()
    private lateinit var binding: FragmentItemListBinding
    private lateinit var userAlbumAdapter: UserAlbumAdapter
    private var userId: Int? = null
    private var albumList: MutableList<AlbumDto> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("id")?.let { userId ->
            albumViewModel.getAlbums(userId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemListBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e(Constants.TAG, "user id $userId")
        initObserver()
    }

    private fun showLoader() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showUserPosts(users: List<AlbumDto>) {
        binding.rvItem.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            userAlbumAdapter = UserAlbumAdapter { id, position ->
                Log.e(Constants.TAG, "album id $id & position $position")
                onAlbumClicked(id, position)
            }
            adapter = userAlbumAdapter
            albumList = users.toMutableList()
            userAlbumAdapter.submitList(users)
            hideLoader()
        }
    }

    private fun showAlbumPhotos(photos: Pair<List<PhotoDto>, Int>) {
        albumList[photos.second].photos = photos.first.map { it.url }
        userAlbumAdapter.submitList(albumList)
    }

    private fun onAlbumClicked(albumId: Int, position: Int) {
        albumViewModel.getAlbumPhotos(albumId, position)
    }

    private fun showError(exception: String) {
        Toast.makeText(requireActivity(), exception, Toast.LENGTH_LONG).show()
    }

    private fun initObserver() {
        albumViewModel.uiState.observe(viewLifecycleOwner, { uiState ->
            when (uiState) {
                is UserAlbumViewModel.UserAlbumsUiState.Error -> showError(uiState.exception)
                UserAlbumViewModel.UserAlbumsUiState.Loading -> showLoader()
                is UserAlbumViewModel.UserAlbumsUiState.Success -> showUserPosts(uiState.posts)
            }
        })

        albumViewModel.uiPhotosState.observe(viewLifecycleOwner, { uiState ->
            when (uiState) {
                is UserAlbumViewModel.UserPhotosUiState.Error -> {
                }
                UserAlbumViewModel.UserPhotosUiState.Loading -> {
                }
                is UserAlbumViewModel.UserPhotosUiState.Success -> showAlbumPhotos(uiState.photos)
            }
        })
    }
}