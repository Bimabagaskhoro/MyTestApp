package com.bimabk.dashboard.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bimabk.common.helper.UiState
import com.bimabk.dashboard.databinding.FragmentCardBinding
import com.bimabk.dashboard.history.adapter.HistoryAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CardFragment : Fragment() {

    private val historyAdapter by lazy { HistoryAdapter() }

    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HistoryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupData()
    }

    private fun setupAdapter() = with(binding) {
        rvHistory.adapter = historyAdapter
    }

    private fun setupData() {
        lifecycleScope.launch {
            viewModel.historyStateCard.collect { state ->
                when (state) {
                    is UiState.Init -> {}
                    is UiState.Loading -> {}
                    is UiState.Success -> {
                        historyAdapter.submitList(state.data)
                    }

                    is UiState.Error -> {}
                }
            }
        }
    }
}
