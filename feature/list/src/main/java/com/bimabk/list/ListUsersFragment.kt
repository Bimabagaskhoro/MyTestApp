package com.bimabk.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bimabk.list.adapter.UserAdapter
import com.bimabk.list.databinding.FragmentListUsersBinding
import com.bimabk.router.RouteTo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListUsersFragment : Fragment() {

    private var _binding: FragmentListUsersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListUsersViewModel by viewModel()

    private lateinit var userAdapter: UserAdapter

    private val router: RouteTo by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observePagingData()

        binding.rvUsers.post {
            binding.rvUsers.postDelayed({
                viewModel.loadMoreUsers()
            }, 3000)
        }
    }

    private fun setupRecyclerView() {
        context?.let {
            userAdapter = UserAdapter { data ->
                val intent = router.routeToDetail(it, data.userId)
                it.startActivity(intent)
            }
        }

        binding.rvUsers.adapter = userAdapter

        binding.rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                val shouldLoadMore = totalItemCount > 0 &&
                        lastVisibleItemPosition >= (totalItemCount - 1) &&
                        !viewModel.isLoading.value &&
                        !viewModel.isLastPage.value

                if (shouldLoadMore) {
                    viewModel.loadMoreUsers()
                }
            }
        })
    }


    private fun observePagingData() {
        var previousSize = 0
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.users.collectLatest { users ->

                if (users.size > previousSize && previousSize > 0) {
                    val newUsers = users.drop(previousSize)
                    userAdapter.addUsers(newUsers)
                } else {
                    userAdapter.submitManualList(users)
                }
                previousSize = users.size
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}