package com.`fun`.gallerydroid.presentation.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.`fun`.gallerydroid.R
import com.`fun`.gallerydroid.databinding.FragmentItemListBinding
import com.`fun`.gallerydroid.domain.model.User
import com.`fun`.gallerydroid.presentation.ui.user.UserListViewModel.UserListUiState.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserListFragment : Fragment() {

    private val userListViewModel by viewModels<UserListViewModel>()
    private lateinit var binding: FragmentItemListBinding
    private lateinit var userListAdapter: UserListAdapter


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
        initObserver()
    }

    private fun showLoader() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showUsers(users: List<User>) {
        binding.rvItem.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            userListAdapter = UserListAdapter {
                id-> onUserClicked(id)
            }
            adapter = userListAdapter
            userListAdapter.submitList(users)
            hideLoader()
        }
    }

    private fun onUserClicked(id: Int) {
        val bundle = bundleOf("id" to id)
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_nav_user_list_to_nav_album_list,bundle)
    }


    private fun showError(exception: String) {
        Toast.makeText(requireActivity(), exception, Toast.LENGTH_LONG).show()
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userListViewModel.uiState.collect { uiState ->
                    when(uiState){
                        is Error -> showError(uiState.exception)
                        Loading -> showLoader()
                        is Success -> showUsers(uiState.users)
                    }
                }
            }
        }
    }
}